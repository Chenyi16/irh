package top.imuster.goods.service;

import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName: RecommendProductService
 * @Description:
 * @author: hmr
 * @date: 2020/5/1 14:03
 */
public interface RecommendProductService {

    /**
     * @Author hmr
     * @Description 根据内容分词
     * @Date: 2020/5/7 20:22
     * @param text
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<java.lang.String>>
     **/
    Message<List<Object>> recommendTagNames(String text) throws IOException;

    /**
     * @Author hmr
     * @Description 分页获得推荐的商品信息
     * @Date: 2020/5/1 14:11
     * @param pageSize
     * @param currentPage
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    Message<Page<ProductInfo>> getOfflineRecommendListByUserId(Integer pageSize, Integer currentPage, Long userId);


    /**
     * @Author hmr
     * @Description 获得实施推荐的商品
     * @Date: 2020/5/14 9:23
     * @param pageSize
     * @param currentPage
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    Message<Page<ProductInfo>> getRealtimeRecommend(Integer pageSize, Integer currentPage, Long userId);

    /**
     * @Author hmr
     * @Description 基于内容的推荐
     * @Date: 2020/5/14 9:43
     * @param pageSize
     * @param currentPage
     * @param productId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    Message<Page<ProductInfo>> getContentRecommend(Integer pageSize, Integer currentPage, Long productId);

    /**
     * @Author hmr
     * @Description 推荐需求
     * @Date: 2020/5/24 14:52
     * @param userId
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductDemandInfo>>
     **/
    Message<Page<ProductDemandInfo>> getDemandRecommend(Long userId, Integer pageSize, Integer currentPage);

    /**
     * @Author hmr
     * @Description 获得redis中保存的商品推荐标签列表
     * @Date: 2020/6/9 11:37
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<java.lang.String>>
     **/
    Message<Page<Object>> getTagList(Integer pageSize, Integer currentPage);

    /**
     * @Author hmr
     * @Description 根据标签名字删除标签
     * @Date: 2020/6/10 11:37
     * @param tagName
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> deleteRecommendTag(String tagName);

}
