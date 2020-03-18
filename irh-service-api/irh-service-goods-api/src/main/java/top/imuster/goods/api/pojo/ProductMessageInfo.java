package top.imuster.goods.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
@ApiModel("商品留言实体类")
public class ProductMessageInfo extends BaseDomain {

	private static final long serialVersionUID = 9834628784L;

	public ProductMessageInfo() {
		//默认无参构造方法
	}
	// 商品留言表主键
	@ApiModelProperty("商品留言表主键")
	private Long id;

	// 商品id
	@ApiModelProperty("商品id")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "参数错误")
	private Long productId;

	// 用户编号
	@ApiModelProperty("用户编号")
	private Long consumerId;

	// 回复消息的id，0表示是新的留言的时候
	@ApiModelProperty("回复消息的id，0表示是新的留言的时候")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "参数错误")
	private Long parentId;

	// 内容, max length: 1000
	@ApiModelProperty("内容")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "参数错误")
	private String content;

	// 1-无效 2-有效
	//private Short state;

	@ApiModelProperty("当前留言下的子节点")
	private List<ProductMessageInfo> childs;

	public List<ProductMessageInfo> getChilds() {
		return childs;
	}

	public void setChilds(List<ProductMessageInfo> childs) {
		this.childs = childs;
	}

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