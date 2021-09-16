<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/header.jsp" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">List Page</h1>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Dashboard v1</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <!-- Main row -->
            <div class="row">
                <!-- Left col -->
                <section class="col-lg-12">
                    <!-- TO DO List -->
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title">Bordered Table</h3>
                            <sec:authorize access="isAuthenticated()">
                            <button type="button" class="btn btn-info btnMod"><a href="/board/register">register</a></button>
                            </sec:authorize>
                        </div>
                        <!-- /.card-header -->
                        <div class="card-body">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th style="width: 20px">BNO</th>
                                    <th>TITLE</th>
                                    <th>WRITER</th>
                                    <th>REGDATE</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${dtoList}" var="dto">
                                    <tr>
                                        <td><c:out value="${dto.bno}"></c:out></td>
                                        <td><a href="javascript:moveRead(${dto.bno})"><c:out value="${dto.title}"></c:out></a></td>
                                            <%--null이면 공백문자나옴--%>
                                        <td><c:out value="${dto.writer}"></c:out></td>
                                        <td><c:out value="${dto.regDate}"></c:out></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <!-- 검색부분 -->
                            <form action="/board/list" method="get">
                                <input type="hidden" name="page" value="1">
                                <input type="hidden" name="size" value="${pageMaker.size}">
                                <!-- 검색 type 선택 -->
                                <div class="col-sm-12">
                                    <!-- select -->
                                    <div class="form-group">
                                        <label>Search</label>
                                        <select name="type" class="custom-select">
                                            <option value="">---</option>
                                            <option value="T" ${pageRequestDTO.type=="T"?"selected":""}>제목</option>
                                            <option value="TC" ${pageRequestDTO.type=="TC"?"selected":""}>제목내용</option>
                                            <option value="C" ${pageRequestDTO.type=="C"?"selected":""}>내용</option>
                                            <option value="TCW" ${pageRequestDTO.type=="TCW"?"selected":""}>제목내용작성자</option>
                                        </select>
                                    </div>
                                </div>
                                <!--keyword 검색창-->
                                <div class="col-sm-9">
                                    <div class="input-group input-group-sm">
                                        <input type="text" class="form-control" name="keyword" value="${pageRequestDTO.keyword}">
                                        <span class="input-group-append"><button type="submit" class="btn btn-info btn-flat">Go!</button></span>
                                    </div>
                                </div>
                            </form>
                            <!-- 검색부분 끝-->

                        </div>

                        <!-- /.card-body -->
                        <div class="card-footer clearfix">
                            <ul class="pagination pagination-sm m-0 float-right">

                                <c:if test="${pageMaker.prev}">
                                    <li class="page-item"><a class="page-link"
                                                             href="javascript:movePage(${pageMaker.start -1})"> 《 </a>
                                    </li>
                                </c:if>

                                <c:forEach begin="${pageMaker.start}" end="${pageMaker.end}" var="num">
                                    <li class="page-item ${pageMaker.page ==num?'active':''}"><a class="page-link"
                                                                                                 href="javascript:movePage(${num})">${num}</a>
                                    </li>
                                </c:forEach>

                                <c:if test="${pageMaker.next}">
                                    <li class="page-item"><a class="page-link"
                                                             href="javascript:movePage(${pageMaker.end +1})"> 》 </a>
                                    </li>
                                </c:if>

                            </ul>
                        </div>
                    </div>
                    <!-- /.card -->
                </section>
                <!-- /.Left col -->
            </div>
            <!-- /.row (main row) -->
        </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
</div>

<div class="modal fade" id="modal-sm">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Small Modal</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>One fine body&hellip;</p>
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<%--<script>
    const result = '${result}' //값이 있으면  const result = '123' / 없으면 '' //모달 띄우기 위한 처리
    console.log("result", result)
<%--</script>--%>

<form id="actionForm" action="/board/list" method="get">
    <input type="hidden" name="page" value="${pageMaker.page}">
    <input type="hidden" name="size" value="${pageMaker.size}">

    <c:if test="${pageRequestDTO.type != null}"> <!--검색조건이 있을때는 붙고 없을때는 떨어져-->
        <input type="hidden" name="type" value="${pageRequestDTO.type}">
        <input type="hidden" name="keyword" value="${pageRequestDTO.keyword}">
    </c:if>
</form>

<%@ include file="../includes/footer.jsp" %>


<script>

    const actionForm = document.querySelector("#actionForm")

    const result = '${result}'

    if (result && result !== '') { //bno가 값이 있거나 공백문자가 아닐 때 띄움
        $('#modal-sm').modal('show')
        window.history.replaceState(null, '', '/board/list') //이전으로 돌아갔을 때 다시 모달창 뜨지 않게 처리
    }

    function movePage(pageNum) {

        //수정하고 돌아가도 원래 보던 페이지 값 유지
        actionForm.querySelector("input[name='page']").setAttribute("value", pageNum)

        actionForm.submit()

    }

    function moveRead(bno) {

        actionForm.setAttribute("action", "/board/read")
        actionForm.innerHTML += `<input type='hidden' name='bno' value='\${bno}'>`
        actionForm.submit()

    }

</script>


</body>
</html>