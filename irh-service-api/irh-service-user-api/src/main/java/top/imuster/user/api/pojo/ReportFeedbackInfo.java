package top.imuster.user.api.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotEmpty;

/**
 * 
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
@ApiModel("用户举报反馈表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportFeedbackInfo extends BaseDomain {

	private static final long serialVersionUID = 5219703142L;

	public ReportFeedbackInfo() {
		//默认无参构造方法
	}
	// 举报反馈表的主键
	@NotEmpty(groups = ValidateGroup.processGroup.class, message = "处理反馈的id不能为空")
	@ApiModelProperty("举报反馈表的主键")
	private Long id;

	// 1-商品举报 2-留言举报 3-评价举报 4-帖子举报
	@ApiModelProperty("1-商品举报 2-商品留言举报 3-商品评价举报 4-帖子举报 5-帖子留言举报")
	private Integer type;

	//  举报对象的id
	@ApiModelProperty("举报对象的id")
	private Long targetId;

	// 举报人id
	@ApiModelProperty("举报人id")
	private Long customerId;

	//处理结果  1-举报失败 2-处理中  3-举报成功
	@ApiModelProperty("处理结果 1-举报失败 2-处理中 3-警告并删除相关内容 4-冻结账号")
	@NotEmpty(groups = ValidateGroup.processGroup.class, message = "处理结果不能为空")
	private Integer result;

	//反馈备注
	@ApiModelProperty("反馈备注")
	private String remark;

	//举报理由
	@ApiModelProperty("举报理由")
	private String reason;

	@ApiModelProperty("处理人")
	private Long processId;

	//总数，用来统计
	private Integer total;

	//举报的类型id，用“,”分隔
	private String reportTypeIds;

	private String pics;

	// 1-无效 2-有效
	//private Integer state


	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public String getReportTypeIds() {
		return reportTypeIds;
	}

	public void setReportTypeIds(String reportTypeIds) {
		this.reportTypeIds = reportTypeIds;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}

	public Long getTargetId() {
		return this.targetId;
	}
    public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	
	public Long getCustomerId() {
		return this.customerId;
	}
    public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	 
}