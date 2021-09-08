<%--
  Created by IntelliJ IDEA.
  User: ahyun
  Date: 2021-09-07
  Time: 오후 2:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

  <input type="file" name="uploadFiles" multiple><button id="uploadBtn">UPLOAD</button>

  <div class="uploadResult">

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
                    str += `<div data-uuid='\${uuid}'><img src="/viewFile?file=\${thumbnail}"/><span>\${fileName}</span>
                            <button onclick="javascript:removeFile('\${fileLink}',this)">x</button></div>`
                }else {
                    str += `<div data-uuid='\${uuid}'><a href="/downFile?file=\${fileLink}">\${fileName}</a></div>`
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

</script>

</body>
</html>
