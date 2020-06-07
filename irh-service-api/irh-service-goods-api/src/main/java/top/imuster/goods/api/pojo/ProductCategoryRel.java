package top.imuster.goods.api.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@ApiModel("商品分类实体类")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductCategoryRel extends BaseDomain {

	private static final long serialVersionUID = 6719726738L;

	public ProductCategoryRel() {
		//默认无参构造方法
	}
	// 商品分类表主键
	@ApiModelProperty("商品分类表主键")
	private Long id;

	// 商品id
	@ApiModelProperty("商品id")
	private Long productId;

	// 分类id
	@ApiModelProperty("分类id")
	private Long categoryId;

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
	
	public Long getCategoryId() {
		return this.categoryId;
	}
    public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}