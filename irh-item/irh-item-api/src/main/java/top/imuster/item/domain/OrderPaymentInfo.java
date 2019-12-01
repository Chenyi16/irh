package top.imuster.item.domain;

import java.util.Date;

import top.imuster.domain.base.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class OrderPaymentInfo extends BaseDomain {

	private static final long serialVersionUID = 11925240015L;

	public OrderPaymentInfo() {
		//默认无参构造方法
	}
	// 订单支付情况表主键
	private Long id;

	// 会员id
	private Long buyerId;

	// 订单id
	private Long orderId;

	// 平台交易号, max length: 100
	private String platformTransactionNum;

	// 10:支付宝 20:线下
	private String paymentType;

	// 1-失败 2-成功
	private Integer paymentState;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getBuyerId() {
		return this.buyerId;
	}
    public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	public Long getOrderId() {
		return this.orderId;
	}
    public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public String getPlatformTransactionNum() {
		return this.platformTransactionNum;
	}
    public void setPlatformTransactionNum(String platformTransactionNum) {
		this.platformTransactionNum = platformTransactionNum;
	}
	
	public String getPaymentType() {
		return this.paymentType;
	}
    public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public Integer getPaymentState() {
		return this.paymentState;
	}
    public void setPaymentState(Integer paymentState) {
		this.paymentState = paymentState;
	}
}