package top.imuster.goods.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;

import java.util.List;

/**
 * ProductDemandInfoDao 接口
 * @author 黄明人
 * @since 2020-01-16 10:19:41
 */
public interface ProductDemandInfoDao extends BaseDao<ProductDemandInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 根据id更新browserTimes
     * @Date: 2020/4/22 03
     * @param update
     * @reture: void
     **/
    void updateBrowserTimesByCondition(List<ProductInfo> update);

    /**
     * @Author hmr
     * @Description 根据主键id查询发布者id
     * @Date: 2020/5/3 16:18
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectUserIdByDemandId(Long id);

    /**
     * @Author hmr
     * @Description 更新demand的收藏
     * @Date: 2020/5/9 9:45
     * @param list
     * @reture: java.lang.Integer
     **/
    Integer updateCollectTotal(List<GoodsForwardDto> list);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/5/24 16:06
     * @param res
     * @reture: java.util.List<top.imuster.goods.api.pojo.ProductDemandInfo>
     **/
    List<ProductDemandInfo> selectInfoByIds(List<Long> res);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/6/1 21:59
     * @param browserTimesDtos
     * @reture: java.lang.Integer
     **/
    Integer updateBrowseTimesByDtoList(List<BrowserTimesDto> browserTimesDtos);
}