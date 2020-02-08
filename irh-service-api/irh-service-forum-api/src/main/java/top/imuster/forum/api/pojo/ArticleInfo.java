package top.imuster.forum.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@ApiModel("文章/帖子表主键")
public class ArticleInfo extends BaseDomain {

	private static final long serialVersionUID = 7095780754L;

	public ArticleInfo() {
		//默认无参构造方法
	}
	// 文章/帖子表主键
	@ApiModelProperty("文章/帖子表主键")
	private Long id;

	// 标题, max length: 255
	@ApiModelProperty("标题")
	private String title;

	// 文章/帖子分类id
	@ApiModelProperty("文章/帖子分类id")
	private Long articleCategory;

	// 封面图片, max length: 255
	@ApiModelProperty("封面图片")
	private String mainPicture;

	// 内容
	@ApiModelProperty("内容")
	private String content;

	//浏览次数
	@ApiModelProperty("浏览次数")
	private Long browserTimes;

	//发布者id
	@ApiModelProperty("发布者id")
	private Long userId;

	@ApiModelProperty("收藏总数")
	private Long collectTotal;

	@ApiModelProperty("点赞总数")
	private Long upTotal;

	//每个文章下的一级留言信息
	@ApiModelProperty("每个文章下的一级留言信息")
	private List<ArticleReview> childs;

	// 1-无效  2-有效
//	private Short state;


	public Long getCollectTotal() {
		return collectTotal;
	}

	public void setCollectTotal(Long collectTotal) {
		this.collectTotal = collectTotal;
	}

	public Long getUpTotal() {
		return upTotal;
	}

	public void setUpTotal(Long upTotal) {
		this.upTotal = upTotal;
	}

	public List<ArticleReview> getChilds() {
		return childs;
	}

	public void setChilds(List<ArticleReview> childs) {
		this.childs = childs;
	}

	public Long getBrowserTimes() {
		return browserTimes;
	}

	public void setBrowserTimes(Long browserTimes) {
		this.browserTimes = browserTimes;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
    public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getArticleCategory() {
		return this.articleCategory;
	}
    public void setArticleCategory(Long articleCategory) {
		this.articleCategory = articleCategory;
	}
	
	public String getMainPicture() {
		return this.mainPicture;
	}
    public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
	
}