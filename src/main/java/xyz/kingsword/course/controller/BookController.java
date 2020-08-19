package xyz.kingsword.course.controller;

import cn.hutool.core.convert.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import xyz.kingsword.course.annocations.Role;
import xyz.kingsword.course.enmu.RoleEnum;
import xyz.kingsword.course.pojo.Book;
import xyz.kingsword.course.pojo.Result;
import xyz.kingsword.course.pojo.param.SelectBookDeclareParam;
import xyz.kingsword.course.service.BookService;
import xyz.kingsword.course.util.BookUtil;
import xyz.kingsword.course.util.Constant;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/book")
@Api(tags = "教材相关接口")
public class BookController {
    @Resource
    private BookService bookService;
    @Resource
    private Constant constant;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation("新增")
    public Result<Object> insert(@RequestBody Book book, String courseId) {
        bookService.insert(book, courseId);
        return Result.emptyResult();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation("更新")
    public Result<Object> update(@RequestBody Book book) {
        bookService.update(book);
        return Result.emptyResult();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation("删除教材")
    public Result<Object> delete(@RequestBody List<Integer> idList, String courseId) {
        bookService.delete(idList, courseId);
        return Result.emptyResult();
    }

    /**
     * 按课程查看教材列表
     *
     * @param courseId 课程id
     */
    @RequestMapping(value = "/getTextBook", method = RequestMethod.GET)
    @ApiOperation("按课程查询教材")
    public Result<Object> getTextBook(String courseId) {
        List<Book> bookList = bookService.getTextBook(courseId);
        return new Result<>(bookList);
    }

    /**
     * 按课程查看参考书列表
     *
     * @param courseId 课程id
     */
    @RequestMapping(value = "/getReferenceBook", method = RequestMethod.GET)
    @ApiOperation("按课程查询参考书")
    public Result<Object> selectReferenceBookByCourse(String courseId) {
        List<Book> bookList = bookService.getReferenceBook(courseId);
        return new Result<>(bookList);
    }

    /**
     * 查看单个教材的详情
     *
     * @param id 教材id
     */
    @RequestMapping(value = "/bookInfo", method = RequestMethod.GET)
    @ApiOperation("查看单个教材的详情")
    public Result<Object> bookInfo(int id) {
        Book book = bookService.getBook(id);
        return new Result<>(book);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("根据id批量查")
    public Result<Object> list(@RequestBody List<Integer> idList) {
        List<Book> bookList = bookService.getByBookIdList(idList);
        return new Result<>(bookList);
    }


    @RequestMapping(value = "/isbn", method = RequestMethod.GET)
    @ApiOperation("教材查询接口，同步远程接口信息")
    public Result<Object> queryBook(@RequestParam("ISBN") String isbn) {
        Book book = BookUtil.getBook(isbn);
        return new Result<>(book);
    }

    @RequestMapping(value = "/getPurchaseStatus", method = RequestMethod.GET)
    @ApiOperation("查看学生订书操作开关")
    @Role(RoleEnum.ACADEMIC_MANAGER)
    public Result<Object> getPurchaseStatus() {
        return new Result<>(Convert.toBool(constant.getPurchaseStatus()));
    }

    @RequestMapping(value = "/selectBookDeclare", method = RequestMethod.POST)
    @ApiOperation("教材申报状态查询")
    @Role(RoleEnum.ACADEMIC_MANAGER)
    public Result<Object> selectBookDeclare(@RequestBody SelectBookDeclareParam param) {
        return new Result<>(bookService.selectBookDeclare(param));
    }
}
