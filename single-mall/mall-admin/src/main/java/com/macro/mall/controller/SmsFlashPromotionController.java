package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsProductCategoryParam;
import com.macro.mall.dto.PmsProductCategoryWithChildrenItem;
import com.macro.mall.model.PmsProductCategory;
import com.macro.mall.model.SmsFlashPromotion;
import com.macro.mall.service.PmsProductCategoryService;
import com.macro.mall.service.SmsFlashPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 限时购活动管理Controller
 * Created on 2018/11/16.
 */
@Controller
@Api(tags = "SmsFlashPromotionController", description = "限时购活动管理")
@RequestMapping("/flash")
public class SmsFlashPromotionController {
    @Autowired
    private SmsFlashPromotionService flashPromotionService;

    @ApiOperation("添加活动")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody SmsFlashPromotion flashPromotion) {
        int count = flashPromotionService.create(flashPromotion);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("编辑活动信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, @RequestBody SmsFlashPromotion flashPromotion) {
        int count = flashPromotionService.update(id, flashPromotion);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除活动信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        int count = flashPromotionService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改上下线状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, Integer status) {
        int count = flashPromotionService.updateStatus(id, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取活动详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@PathVariable Long id) {
        SmsFlashPromotion flashPromotion = flashPromotionService.getItem(id);
        return CommonResult.success(flashPromotion);
    }

    @ApiOperation("根据活动名称分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsFlashPromotion> flashPromotionList = flashPromotionService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(flashPromotionList));
    }

    /**
     * 商品分类模块Controller
     * Created on 2022/4/26.
     */
    @Controller
    @Api(tags = "PmsProductCategoryController", description = "商品分类管理")
    @RequestMapping("/productCategory")
    public static class PmsProductCategoryController {
        @Autowired
        private PmsProductCategoryService productCategoryService;

        @ApiOperation("添加产品分类")
        @RequestMapping(value = "/create", method = RequestMethod.POST)
        @ResponseBody
        @PreAuthorize("hasAuthority('pms:productCategory:create')")
        public CommonResult create(@Validated @RequestBody PmsProductCategoryParam productCategoryParam,
                             BindingResult result) {
            int count = productCategoryService.create(productCategoryParam);
            if (count > 0) {
                return CommonResult.success(count);
            } else {
                return CommonResult.failed();
            }
        }

        @ApiOperation("修改商品分类")
        @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
        @ResponseBody
        @PreAuthorize("hasAuthority('pms:productCategory:update')")
        public CommonResult update(@PathVariable Long id,
                             @Validated
                             @RequestBody PmsProductCategoryParam productCategoryParam,
                             BindingResult result) {
            int count = productCategoryService.update(id, productCategoryParam);
            if (count > 0) {
                return CommonResult.success(count);
            } else {
                return CommonResult.failed();
            }
        }

        @ApiOperation("分页查询商品分类")
        @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
        @ResponseBody
        @PreAuthorize("hasAuthority('pms:productCategory:read')")
        public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
            List<PmsProductCategory> productCategoryList = productCategoryService.getList(parentId, pageSize, pageNum);
            return CommonResult.success(CommonPage.restPage(productCategoryList));
        }

        @ApiOperation("根据id获取商品分类")
        @RequestMapping(value = "/{id}", method = RequestMethod.GET)
        @ResponseBody
        @PreAuthorize("hasAuthority('pms:productCategory:read')")
        public CommonResult<PmsProductCategory> getItem(@PathVariable Long id) {
            PmsProductCategory productCategory = productCategoryService.getItem(id);
            return CommonResult.success(productCategory);
        }

        @ApiOperation("删除商品分类")
        @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
        @ResponseBody
        @PreAuthorize("hasAuthority('pms:productCategory:delete')")
        public CommonResult delete(@PathVariable Long id) {
            int count = productCategoryService.delete(id);
            if (count > 0) {
                return CommonResult.success(count);
            } else {
                return CommonResult.failed();
            }
        }

        @ApiOperation("修改导航栏显示状态")
        @RequestMapping(value = "/update/navStatus", method = RequestMethod.POST)
        @ResponseBody
        @PreAuthorize("hasAuthority('pms:productCategory:update')")
        public CommonResult updateNavStatus(@RequestParam("ids") List<Long> ids, @RequestParam("navStatus") Integer navStatus) {
            int count = productCategoryService.updateNavStatus(ids, navStatus);
            if (count > 0) {
                return CommonResult.success(count);
            } else {
                return CommonResult.failed();
            }
        }

        @ApiOperation("修改显示状态")
        @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
        @ResponseBody
        @PreAuthorize("hasAuthority('pms:productCategory:update')")
        public CommonResult updateShowStatus(@RequestParam("ids") List<Long> ids, @RequestParam("showStatus") Integer showStatus) {
            int count = productCategoryService.updateShowStatus(ids, showStatus);
            if (count > 0) {
                return CommonResult.success(count);
            } else {
                return CommonResult.failed();
            }
        }

        @ApiOperation("查询所有一级分类及子分类")
        @RequestMapping(value = "/list/withChildren", method = RequestMethod.GET)
        @ResponseBody
        @PreAuthorize("hasAuthority('pms:productCategory:read')")
        public CommonResult<List<PmsProductCategoryWithChildrenItem>> listWithChildren() {
            List<PmsProductCategoryWithChildrenItem> list = productCategoryService.listWithChildren();
            return CommonResult.success(list);
        }
    }
}