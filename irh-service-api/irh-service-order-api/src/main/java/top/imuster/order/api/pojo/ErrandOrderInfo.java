package top.imuster.order.api.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
@ApiModel("跑腿订单实体类")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrandOrderInfo extends BaseDomain {

	private static final long serialVersionUID = 11790645230L;

	public ErrandOrderInfo() {
		//默认无参构造方法
	}
	// 跑腿订单表主键
	@ApiModelProperty("跑腿订单表主键")
	private Long id;

	// 订单编号, max length: 64
	@ApiModelProperty("订单编号")
	private String orderCode;

	// 跑腿信息表主键
	@ApiModelProperty("跑腿信息表主键")
	private Long errandId;

	// 接单者id
	@ApiModelProperty("接单者id")
	private Long holderId;

	// 发布者id
	@ApiModelProperty("发布者id")
	private Long publisherId;

	// 支付金额
	@ApiModelProperty("支付金额")
	private String payMoney;

	// 订单创建时间
//	private Date createTime;

	// 订单完成时间, max length: 20
	@ApiModelProperty("订单完成时间")
	private String finishTime;

	//跑腿的版本
	private Integer errandVersion;

	// 1-取消订单  2-删除  3-未完成 4-已完成 5-下单失败  6-发布者删除  7-接单者删除
//	private Short state;

	private String address;

	private String phoneNum;

	//要求时间
	private String requirement;

	//是否评价,1-未评价  2-已评价
	private Integer evaluateState;

	public Integer getEvaluateState() {
		return evaluateState;
	}

	public void setEvaluateState(Integer evaluateState) {
		this.evaluateState = evaluateState;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getErrandVersion() {
		return errandVersion;
	}

	public void setErrandVersion(Integer errandVersion) {
		this.errandVersion = errandVersion;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getOrderCode() {
		return this.orderCode;
	}
    public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	public Long getErrandId() {
		return this.errandId;
	}
    public void setErrandId(Long errandId) {
		this.errandId = errandId;
	}
	
	public Long getHolderId() {
		return this.holderId;
	}
    public void setHolderId(Long holderId) {
		this.holderId = holderId;
	}
	
	public Long getPublisherId() {
		return this.publisherId;
	}
    public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	
	public String getPayMoney() {
		return this.payMoney;
	}
    public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	
	public String getFinishTime() {
		return this.finishTime;
	}
    public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	
}