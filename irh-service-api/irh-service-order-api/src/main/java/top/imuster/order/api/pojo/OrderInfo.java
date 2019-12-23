package top.imuster.order.api.pojo;

import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class OrderInfo extends BaseDomain {

	private static final long serialVersionUID = 18856694430L;

	public OrderInfo() {
		//默认无参构造方法
	}
	// 订单表主键
    @NotNull(groups = ValidateGroup.prePayment.class, message = "订单主键不能为空")
	private Long id;

	//订单编号,必须保证唯一,且64位之内64个字符以内,只能包含字母、数字、下划线
	@NotNull(groups = ValidateGroup.prePayment.class, message = "订单编号不能为空")
	private String orderCode;

	// 会员表的id
	@NotNull(groups = ValidateGroup.prePayment.class, message = "卖家的id不能为空")
	private Long salerId;

	// 会员表的nickname字段, max length: 255
	private String salerNickname;

	// 会员表的id
	private Long buyerId;

	// 商品id
	@NotNull(groups = ValidateGroup.prePayment.class, message = "商品id不能为空")
	private Long productId;

	// 支付金额
	@NotNull(groups = ValidateGroup.prePayment.class, message = "支付金额不能为空")
	private String paymentMoney;

	// 订单标题, max length: 1000
	@NotNull(groups = ValidateGroup.prePayment.class, message = "订单编号不能为空")
	private String orderRemark;

	// 送货地址:将楼号、楼层、宿舍号以json格式存储
	private String address;

	// 10:线上交易 20:线下交易 30:公益捐赠
	private Integer tradeType;

	// 支付时间
	private Date paymentTime;

	// 交易完成时间,用户确定收货的时间
	private Date finishTime;

	// 10:订单超时 20:取消订单 30:删除订单 40:交易成功
	//private Short state;


	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSalerId() {
		return this.salerId;
	}
    public void setSalerId(Long salerId) {
		this.salerId = salerId;
	}
	
	public String getSalerNickname() {
		return this.salerNickname;
	}
    public void setSalerNickname(String salerNickname) {
		this.salerNickname = salerNickname;
	}
	
	public Long getBuyerId() {
		return this.buyerId;
	}
    public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	public Long getProductId() {
		return this.productId;
	}
    public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getPaymentMoney() {
		return this.paymentMoney;
	}
    public void setPaymentMoney(String paymentMoney) {
		this.paymentMoney = paymentMoney;
	}
	
	public String getOrderRemark() {
		return this.orderRemark;
	}
    public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
	public String getAddress() {
		return this.address;
	}
    public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getTradeType() {
		return this.tradeType;
	}
    public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	
	public Date getPaymentTime() {
		return this.paymentTime;
	}
    public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	public Date getFinishTime() {
		return this.finishTime;
	}
    public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
}