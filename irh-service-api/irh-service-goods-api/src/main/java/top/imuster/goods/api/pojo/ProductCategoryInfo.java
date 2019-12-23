package top.imuster.goods.api.pojo;


import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class ProductCategoryInfo extends BaseDomain {

	private static final long serialVersionUID = 6182073821L;

	public ProductCategoryInfo() {
		//默认无参构造方法
	}
	// 商品分类信息表的主键
	@NotBlank(groups = ValidateGroup.editGroup.class)
	private Long id;

	@NotBlank(groups = ValidateGroup.addGroup.class)
	private Long parentId;
	// 当值为0的时候标识根节点

	// 分类名称, max length: 255
	@NotBlank(groups = ValidateGroup.addGroup.class)
	private String name;

	// 分类的描述
	@NotBlank(groups = ValidateGroup.addGroup.class)
	private String desc;

	// 1:无效  2:有效
	//private Short state;

	//子节点
	public List<ProductCategoryInfo> childs = new ArrayList<>();

	@Override
	public String toString() {
		return "ProductCategoryInfo{" +
				"id=" + id +
				", parentId=" + parentId +
				", name='" + name + '\'' +
				", desc='" + desc + '\'' +
				", childs=" + childs +
				'}';
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}

	public List<ProductCategoryInfo> getChilds() {
		return childs;
	}

	public void setChilds(List<ProductCategoryInfo> childs) {
		this.childs = childs;
	}

	public Long getParentId() {
		return this.parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return this.name;
	}
    public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}
    public void setDesc(String desc) {
		this.desc = desc;
	}
}