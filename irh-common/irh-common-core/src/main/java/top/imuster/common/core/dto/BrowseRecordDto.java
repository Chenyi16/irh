package top.imuster.common.core.dto;

import top.imuster.common.core.enums.BrowserType;

import java.io.Serializable;

/**
 * @ClassName: BrowseRecordDto
 * @Description: 浏览历史
 * @author: hmr
 * @date: 2020/3/28 9:56
 */
public class BrowseRecordDto implements Serializable {

    private static final long serialVersionUID = -2209880179800382887L;

    //浏览的对象id
    private Long targetId;

    //浏览的类型
    private BrowserType browserType;

    //用户id
    private Long userId;

    //浏览时间
    private Long browserTime;

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBrowserTime() {
        return browserTime;
    }

    public void setBrowserTime(Long browserTime) {
        this.browserTime = browserTime;
    }
}
