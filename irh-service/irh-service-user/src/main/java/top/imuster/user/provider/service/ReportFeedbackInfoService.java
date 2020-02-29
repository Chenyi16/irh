package top.imuster.user.provider.service;

import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.ReportFeedbackInfo;

/**
 * ReportFeedbackInfoService接口
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
public interface ReportFeedbackInfoService extends BaseService<ReportFeedbackInfo, Long> {

    /**
     * @Description: 管理员处理用户提交的举报
     * @Author: hmr
     * @Date: 2020/1/11 16:35
     * @param reportFeedbackInfo
     * @param userId
     * @reture: void
     **/
    void processReport(ReportFeedbackInfo reportFeedbackInfo, Long userId) throws Exception;

    /**
     * @Author hmr
     * @Description 查询举报记录
     * @Date: 2020/2/29 14:52
     * @param page
     * @reture: top.imuster.common.base.domain.Page<top.imuster.user.api.pojo.ReportFeedbackInfo>
     **/
    Page<ReportFeedbackInfo> list(Page<ReportFeedbackInfo> page);

    /**
     * @Author hmr
     * @Description 根据举报对象的id处理请求
     * @Date: 2020/2/29 15:13
     * @param targetId
     * @param result
     * @param remark
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> processReportByTargetId(Long targetId, Integer result, String remark, Long userId);
}