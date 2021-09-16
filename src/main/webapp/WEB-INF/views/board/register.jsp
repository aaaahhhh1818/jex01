<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/header.jsp"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">Register Page</h1>
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
                            <h3 class="card-title">Board Register</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <form id="form1" action="/board/register" method="post">
                            <div class="card-body">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">title</label>
                                    <input type="text" name="title" class="form-control" id="exampleInputEmail1" placeholder="Enter Title">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail12">Writer</label>
                                    <input type="text" name="writer" class="form-control" id="exampleInputEmail12" placeholder="Enter Writer" readonly value="<sec:authentication property="principal.mid"/>">
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <!-- textarea -->
                                        <div class="form-group">
                                            <label>Textarea</label>
                                            <textarea name="content" class="form-control" rows="3" placeholder="Enter ..."></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /.card-body -->

                            <div class="temp">

                            </div>

                            <div class="card-footer">
                                <button type="submit" id="submitBtn" class="btn btn-primary">Submit</button>
                            </div>

                        </form>

                        <style>
                            .uploadResult {
                                display: flex;
                                justify-content: center;
                                flex-direction: row;
                            }
                        </style>

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

                        </div>

                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
    </section>
</div>

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
                            <button onclick="javascript:removeFile('\${fileLink}',this)">x</button></div>`
                }else {
                    str += `<div data-uuid='\${uuid}' data-filename='\${fileName}' data-uploadpath='\${uploadPath}' data-image='\${image}'>
                            <a href="/downFile?file=\${fileLink}">\${fileName}</a><button onclick="javascript:removeFile('\${fileLink}',this)">x</button></div>`
                }

            }//enf for
            uploadResultDiv.innerHTML += str //업로드 여러번 할 수 있기 때문에 기존에 있던애 유지하면서 추가

        })


    }, false)

    function removeFile(fileLink, ele) {
        console.log(fileLink)
        axios.post("/removeFile", {fileName: fileLink}).then(response => {
            const targetDiv = ele.parentElement
            targetDiv.remove()
        })
    }

    const form1 = document.querySelector("#form1")

    document.querySelector("#submitBtn").addEventListener("click", (e) => {
        e.stopPropagation()
        e.preventDefault()
        //현재 화면에 있는 파일 정보를 hidden 태그들로 변화
        const fileDivArr = uploadResultDiv.querySelectorAll("div") //배열의 크기가 첨부파일 개수임

        if(!fileDivArr) { //없으면 그냥 빈값 넘기고 있으면 루프 돌려야함
            form1.submit()
            return
        }

        let str = ""
        for(let i = 0; i < fileDivArr.length; i++) {
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
        //form1.innerHTMl += str
        form1.submit()
        //form을 submit
    }, false)

</script>

<%@ include file="../includes/footer.jsp"%>

</body>
</html>