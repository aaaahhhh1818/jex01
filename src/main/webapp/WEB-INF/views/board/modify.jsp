<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/header.jsp" %>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">Modify Page</h1>
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
            <div class="row">
                <!-- left column -->
                <div class="col-md-12">
                    <!-- general form elements -->
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Board Modify</h3>
                        </div>
                        <!-- /.card-header -->
                    <form id="form1"> <%--실제로 날라가는애--%>
                        <input type="hidden" name="page" value="${pageRequestDTO.page}">
                        <input type="hidden" name="size" value="${pageRequestDTO.size}">

                        <c:if test="${pageRequestDTO.type != null}">
                            <input type="hidden" name="type" value="${pageRequestDTO.type}">
                            <input type="hidden" name="keyword" value="${pageRequestDTO.keyword}">
                        </c:if>

                        <div class="card-body">
                            <div class="form-group">
                                <label for="exampleInputEmail1">BNO</label>
                                <input type="text" name="bno" class="form-control" id="exampleInputEmail1" value="<c:out value="${boardDTO.bno}"></c:out>" readonly>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail12">TITLE</label>
                                <input type="text" name="title" class="form-control" id="exampleInputEmail12" value="<c:out value="${boardDTO.title}"></c:out>">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail13">WRITER</label>
                                <input type="text" name="writer" class="form-control" id="exampleInputEmail13" value="<c:out value="${boardDTO.writer}"></c:out>" readonly>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <!-- textarea -->
                                    <div class="form-group">
                                        <label>Textarea</label>
                                        <textarea name="content" class="form-control" rows="3"><c:out value="${boardDTO.content}"></c:out></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--첨부파일 데이터 hidden으로 넣어주기-->
                        <div class="temp">

                        </div>
                        <!-- /.card-body -->

                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary btnList">목록</button>
                            <button type="submit" class="btn btn-warning btnMod">수정</button>
                            <button type="submit" class="btn btn-danger btnDel">삭제</button>
                        </div>
                    </form>
                    </div>

                    <!--첨부파일 추가 부분-->
                    <label for="exampleInputFile">File input</label>
                    <div class="input-group">
                        <div class="custom-file">
                            <input type="file" name="uploadFiles" class="custom-file-input" id="exampleInputFile" multiple>
                            <label class="custom-file-label" for="exampleInputFile">Choose file</label>
                        </div>
                        <div class="input-group-append">
                            <span class="input-group-text" id="uploadBtn">Upload</span>
                        </div>
                    </div>

                    <div class="uploadResult">
                        <c:forEach items="${boardDTO.files}" var="attach">
                            <div data-uuid="${attach.uuid}" data-filename="${attach.fileName}" data-uploadpath="${attach.uploadPath}" data-image="${attach.image}">
                                <c:if test="${attach.image}">
                                    <img src="/viewFile?file=${attach.getThumbnail()}">
                                </c:if>
                                <span>${attach.fileName}</span>
                                <button onclick="javascript:removeDiv(this)">x</button><!--파일 자체를 지우면 안되고 div만 지워서 처리해줌 -->
                            </div>
                        </c:forEach>
                    </div>

                    <!-- /.card -->
                </div>
            </div>
        </div>
    </section>
</div>

<form id="actionForm" action="/board/list" method="get">
    <input type="hidden" name="page" value="${pageRequestDTO.page}">
    <input type="hidden" name="size" value="${pageRequestDTO.size}">

    <c:if test="${pageRequestDTO.type != null}"> <!--검색조건이 있을때는 붙고 없을때는 떨어져-->
        <input type="hidden" name="type" value="${pageRequestDTO.type}">
        <input type="hidden" name="keyword" value="${pageRequestDTO.keyword}">
    </c:if>
</form>

<%@ include file="../includes/footer.jsp" %>

<script>

    const form = document.querySelector("#form1")
    const actionForm = document.querySelector("#actionForm")

    document.querySelector(".btnList").addEventListener("click", (e) => {
        e.preventDefault()
        e.stopPropagation()

        // form.setAttribute("action","/board/list")
        // form.setAttribute("method","get")

        // window.location = "/board/list"

        actionForm.submit();
    },false)

    document.querySelector(".btnDel").addEventListener("click", (e) => {
        e.preventDefault()
        e.stopPropagation()

        form.setAttribute("action","/board/remove")
        form.setAttribute("method","post")
        form.submit()

    },false)

    document.querySelector(".btnMod").addEventListener("click", (e) => {
        e.preventDefault()
        e.stopPropagation()

        const fileDivArr = uploadResultDiv.querySelectorAll("div") //배열의 크기가 첨부파일 개수임

        if(fileDivArr && fileDivArr.length > 0) { //div 값이 있을 때만 의미있는 코드 !

            let str = ""
            for (let i = 0; i < fileDivArr.length; i++) {
                const target = fileDivArr[i]
                const uuid = target.getAttribute("data-uuid")
                const fileName = target.getAttribute("data-filename")
                const uploadPath = target.getAttribute("data-uploadpath")
                const image = target.getAttribute("data-image")

                str += `<input type='hidden' name='files[\${i}].uuid' value='\${uuid}'>`
                str += `<input type='hidden' name='files[\${i}].fileName' value='\${fileName}'>`
                str += `<input type='hidden' name='files[\${i}].uploadPath' value='\${uploadPath}'>`
                str += `<input type='hidden' name='files[\${i}].image' value='\${image}'>`
            }
            document.querySelector(".temp").innerHTML = str
        }//end if


        //얘네는 첨부파일 없어도 존재 해야함
        form.setAttribute("action","/board/modify")
        form.setAttribute("method","post")

        form.submit()

    },false)


</script>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script>

    const uploadResultDiv = document.querySelector(".uploadResult")

    document.querySelector("#uploadBtn").addEventListener("click", (e) => {

        const formData = new FormData()
        const fileInput = document.querySelector("input[name='uploadFiles']")

        for(let i = 0; i < fileInput.files.length; i++) {
            //같은이름으로 여러개 담는게 핵심..?
            formData.append("uploadFiles", fileInput.files[i]) // 이 이름이 파라미터가 됨 (controller에서)
        }

        console.dir(formData)

        const headerObj = { headers: {'Content-Type': 'multipart/form-data'}} //헤더 정보 보내는거

        //axios로 데이터 불러오기
        axios.post("/upload", formData, headerObj).then((response) => {
            const arr = response.data
            console.log(arr)
            let str = ""
            for(let i = 0; i < arr.length; i++) {

                const {uuid, fileName, uploadPath, image, thumbnail, fileLink} = {...arr[i]} //스프레드 연산자 써서 값 꺼냄

                if(image) {
                    str += `<div data-uuid='\${uuid}' data-filename='\${fileName}' data-uploadpath='\${uploadPath}' data-image='\${image}'>
                            <img src="/viewFile?file=\${thumbnail}"/><span>\${fileName}</span>
                            <button onclick="javascript:removeDiv(this)">x</button></div>`
                }else {
                    str += `<div data-uuid='\${uuid}' data-filename='\${fileName}' data-uploadpath='\${uploadPath}' data-image='\${image}'>
                            <a href="/downFile?file=\${fileLink}">\${fileName}</a><button onclick="javascript:removeDiv(this)">x</button></div>`
                }

            }//enf for
            uploadResultDiv.innerHTML += str //업로드 여러번 할 수 있기 때문에 기존에 있던애 유지하면서 추가
        })
    }, false)

    function removeDiv(ele) { //등록 할 때 처럼 실제로 삭제되는건 아니고 화면상에서만 삭제처리
        ele.parentElement.remove()
    }

</script>


</body>
</html>