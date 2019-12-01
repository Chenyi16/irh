package top.imuster.item.pojo;


import top.imuster.domain.base.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
public class ProductMessage extends BaseDomain {

	private static final long serialVersionUID = 9834628784L;

	public ProductMessage() {
		//默认无参构造方法
	}
	// 商品留言表主键
	private Long id;

	// 商品id
	private Long productId;

	// 用户编号
	private Long consumerId;

	// 回复消息的id，-1表示是新的留言的时候 
	private Long parentId;

	// 内容, max length: 1000
	private String content;

	// 1-无效 2-有效
	//private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProductId() {
		return this.productId;
	}
    public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getConsumerId() {
		return this.consumerId;
	}
    public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
}