package top.imuster.user.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.service.ReportFeedbackInfoService;
import top.imuster.user.provider.service.UserInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: CustomerController
 * @Description: customer的控制器
 * @author: hmr
 * @date: 2019/12/18 19:11
 */
@Api(tags = "用户controller,这个控制器主要是对自己信息的一些操作")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    ReportFeedbackInfoService reportFeedbackInfoService;

    @Resource
    UserInfoService userInfoService;

    /**
     * @Description 用户在注册的时候需要校验各种参数
     * @Author hmr
     * @Date 12:53 2020/1/14
     * @Param checkValidDto
     * @param bindingResult
     * @return top.imuster.common.base.wrapper.Message 
     **/
    @ApiOperation(value = "修改信息前需要校验各种参数(用户名、邮箱、手机号等)必须唯一",httpMethod = "POST")
    @PostMapping("/check")
    public Message<String> checkValid(@Validated(ValidateGroup.addGroup.class) @RequestBody CheckValidDto checkValidDto, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        boolean flag = userInfoService.checkValid(checkValidDto);
        if(flag){
            return Message.createBySuccess();
        }
        return Message.createByError(checkValidDto.getType().getTypeName() + "已经存在");
    }

    @ApiOperation("获得个人信息")
    @GetMapping("/detail")
   // @NeedLogin
    public Message<UserInfo> getUserInfoById(){
        UserInfo userInfo = userInfoService.selectEntryList(getCurrentUserIdFromCookie()).get(0);
        userInfo.setPassword("");
        return Message.createBySuccess(userInfo);
    }

    /**
     * @Description: 修改会员的个人信息
     * @Author: hmr
     * @Date: 2019/12/26 19:37
     * @param userInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/edit")
    @ApiOperation(value = "修改会员的个人信息(先校验一些信息是否存在),以表单的形式上传,不是用json,其中表单中各个标签的按钮name必须和实体类保持一致", httpMethod = "POST")
    public Message<String> editInfo(@ApiParam("ConsumerInfo实体类") @Validated(ValidateGroup.editGroup.class) @RequestBody UserInfo userInfo, BindingResult bindingResult) throws Exception{
        validData(bindingResult);
        userInfoService.updateByKey(userInfo);
        return Message.createBySuccess();
    }

    /**
     * @Description: 用户举报
     * @Author: hmr
     * @Date: 2020/1/11 12:18
     * @param type
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @GetMapping("/report/{type}/{id}")
    @ApiOperation(value = "用户举报(type可选择 1-商品举报 2-留言举报 3-评价举报 4-帖子举报),id则为举报对象的id", httpMethod = "GET")
    public Message<String> reportFeedback(@ApiParam("1-商品举报 2-留言举报 3-评价举报 4-帖子举报")@PathVariable("type") Integer type, @ApiParam("举报对象的id") @PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        Long userId = getCurrentUserIdFromCookie();
        ReportFeedbackInfo reportFeedbackInfo = new ReportFeedbackInfo();
        reportFeedbackInfo.setCustomerId(userId);
        reportFeedbackInfo.setType(type);
        reportFeedbackInfo.setTargetId(id);
        reportFeedbackInfo.setState(2);
        reportFeedbackInfoService.insertEntry(reportFeedbackInfo);
        return Message.createBySuccess("反馈成功,我们会尽快处理");
    }

    @ApiOperation(value = "根据用户id获得用户基本信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message<UserDto> getUserNameById(@PathVariable("id") String id){
        if(id == null || StringUtils.isEmpty(id) || "null".equals(id)) return Message.createByError("参数错误");
        return userInfoService.getUserDtoByUserId(Long.parseLong(id));
    }

    @ApiOperation("查看用户账号的状态")
    @GetMapping("/state")
    @NeedLogin
    public Message<Long> getUserState(){
        UserDto userInfo = getCurrentUserFromCookie();
        return Message.createBySuccess(userInfo.getUserId());
    }

    @ApiOperation("更新用户头像")
    @NeedLogin
    @PostMapping("/portrait")
    public Message<String> updateUserPortrait(@RequestBody String picUrl){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(getCurrentUserIdFromCookie());
        userInfo.setPortrait(picUrl);
        userInfoService.updateByKey(userInfo);
        return Message.createBySuccess();
    }

}
