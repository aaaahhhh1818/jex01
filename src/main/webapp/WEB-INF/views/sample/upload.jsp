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

  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>


<script>

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
        axios.post("/upload", formData, headerObj)


    }, false)

</script>

</body>
</html>
