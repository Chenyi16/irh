package top.imuster.file.provider.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.file.provider.file.FastDFSFile;
import top.imuster.file.provider.utils.FastDFSUtil;

/**
 * @Description: 文件上传代码实现
 * @Author: lpf
 * @Date: 2019/12/18 11:18
 * @reture: 文件的URL路径
 **/
@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController extends BaseController {

    /**
     * @Description:
     * @Author: lpf
     * @Date: 2019/12/23 12:54
     * @param file
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @PostMapping
    public Message upload(@RequestParam(value = "file")MultipartFile file) throws Exception {
        //疯转文件信息
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),   //文件名 1.jpg
                file.getBytes(),              //文件的字节数组
                org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename())    //获取文件拓展名
        );

        //调用FastDFSUtil工具类将文件上传到FastDFS中
        String[] uploads = FastDFSUtil.upload(fastDFSFile);

        //拼接访问地址 url = http://39.105.0.169:8080/group1/M00/00/00/hjdfhjhfjs3278yf47.jpg
        //String url = "http://39.105.0.169:8080/" + uploads[0] + "/" + uploads[1];
        String url = FastDFSUtil.getTrackerInfo()+ "/" + uploads[0] + "/" + uploads[1];
        return Message.createBySuccess("文件上传成功", url);
    }



}
