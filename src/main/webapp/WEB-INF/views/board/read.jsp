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
                    <h1 class="m-0">Read Page</h1>
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
                            <h3 class="card-title">Board Read</h3>
                        </div>
                        <!-- /.card-header -->
                        <div class="card-body">
                            <div class="form-group">
                                <label for="exampleInputEmail1">BNO</label>
                                <input type="text" name="bno" class="form-control" id="exampleInputEmail1" value="<c:out value="${boardDTO.bno}"></c:out>" readonly>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail12">TITLE</label>
                                <input type="text" name="title" class="form-control" id="exampleInputEmail12" value="<c:out value="${boardDTO.title}"></c:out>" readonly>
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
                                        <textarea name="content" class="form-control" rows="3" disabled><c:out value="${boardDTO.content}"></c:out></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.card-body -->

                        <div class="card-footer float-right">
                            <button type="button" class="btn btn-default btnList">LIST</button>
                            <sec:authentication property="principal" var="memberDTO"/>

                            <c:if test="${boardDTO.writer eq memberDTO.mid}">
                            <button type="button" class="btn btn-info btnMod">MODIFY</button>
                            </c:if>
                        </div>

                        <!-- 파일정보 뿌려주기 -->
                        <div>
                            <c:forEach items="${boardDTO.files}" var="attach">
                                <div>
                                    <c:if test="${attach.image}">
                                        <img onclick="javascript:showOrigin('${attach.getFileLink()}')" src="/viewFile?file=${attach.getThumbnail()}">
                                    </c:if>
                                    <span>${attach.fileName}</span>
                                </div>
                            </c:forEach>
                        </div>


                    </div>
                    <!-- /.card -->

                    <!-- 댓글 -->
                    <!-- DIRECT CHAT -->
                    <div class="card direct-chat direct-chat-primary">
                        <div class="card-header">
                            <h3 class="card-title">Replies</h3>

                            <div class="card-tools">
                                <span title="3 New Messages" class="badge badge-primary addReplyBtn">Add Reply</span>
                                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                                <button type="button" class="btn btn-tool" title="Contacts" data-widget="chat-pane-toggle">
                                    <i class="fas fa-comments"></i>
                                </button>
                                <button type="button" class="btn btn-tool" data-card-widget="remove">
                                    <i class="fas fa-times"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.card-header -->
                        <div class="card-body">
                            <!-- Conversations are loaded here -->
                            <div class="direct-chat-messages">
                                <!-- 댓글 뿌려주기 -->
                                <!-- /.direct-chat-msg -->
                            </div>
                            <!--/.direct-chat-messages-->
                        </div>
                    </div>
                    <!--/.direct-chat -->
                    <!-- 댓글 끝 -->

                </div>
            </div>
        </div>
    </section>
</div>

<!--모달 -->
<div class="modal fade" id="modal-sm">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Reply</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="text" name="replyer">
                <input type="text" name="reply">
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary operBtn">Save changes</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!--수정 삭제 모달 -->
<div class="modal fade" id="modal-lg">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Modify/Remove</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="rno">
                <input type="text" name="replyerMod">
                <input type="text" name="replyMod">
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-info btnModReply">Modify</button>
                <button type="button" class="btn btn-danger btnRem">Remove</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!--이미지 띄우는 모달 -->
<div class="modal fade" id="modal-image">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
            <div class="modal-body">
                <img id="targetImage">
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

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

    const actionForm = document.querySelector("#actionForm") //document.querySelector 계속 안쓰려고 미리 만들어 놓는 것

    document.querySelector(".btnList").addEventListener("click", () => {actionForm.submit()}, false)

    document.querySelector(".btnMod").addEventListener("click", () => {

        const bno = '${boardDTO.bno}'

        actionForm.setAttribute("action", "/board/modify")
        actionForm.innerHTML += `<input type='hidden' name='bno' value='\${bno}'>`
        actionForm.submit()
    }, false)

</script>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="/resources/js/reply.js"></script>

<script>

    const modalImage = new bootstrap.Modal(document.querySelector('#modal-image'))

    function showOrigin(fileLink) {

        //alert(fileLink)
        document.querySelector("#targetImage").src = `/viewFile?file=\${fileLink}` //src 실시간으로 받는게 더 빠름
        modalImage.show()

    }

    function after(result) {
        console.log("after..............")
        console.log("result", result)
    }

    //doA().then(result => console.log(result))

    //doB(after) // ()실행한 객체, ()없으면 순수한 함수

    //const reply = {bno:323, replyer:'user00', reply:'323323323323'} //js 객체 -> 댓글 임의로 등록한 것

    //doC(reply).then(result => console.log(result))

    //doD(112).then(result => console.log(result))

    //객체전달
    //const reply = {rno:112, reply:"Update reply text..."}

    //doE(reply).then(result => console.log(result))

    function getList() {
        const target = document.querySelector(".direct-chat-messages") //댓글 뿌려주는 부분 class로 잡아줌
        const bno = '${boardDTO.bno}' //EL태그임 //230


        function convertTemp(replyObj) {

            const { rno, bno, reply, replyer, replyDate, modDate } = {...replyObj} //변수들 펼쳐서 넣어줌

            const temp =`<div class="direct-chat-msg">
                <div class="direct-chat-infos clearfix">
                    <span class="direct-chat-name float-left">\${rno}--\${replyer}</span>
                    <span class="direct-chat-timestamp float-right">\${replyDate}</span>
                </div>
                <div class="direct-chat-text" data-rno='\${rno}' data-replyer='\${replyer}'>\${reply}</div>
            </div>`

            return temp

        }

        getReplyList(bno).then(data => {
            console.log(data) //현재 데이터 배열. 왜 즉시실행함수 사용 안하고 함수로 뺐을까? //댓글을 추가할 때 문제가됨
            let str ="";

            data.forEach(reply => {
                str += convertTemp(reply)
            })
            target.innerHTML = str
        })
    }

    //최초 실행
    (function() {
        getList()
    })()

    const modalDiv = $("#modal-sm")

    let oper = null //변수명 잡아줘야함

    document.querySelector(".addReplyBtn").addEventListener("click", function() {

        oper = 'add'

        modalDiv.modal('show')

    }, false)

    //전송하기 전 화면 처리
    document.querySelector(".operBtn").addEventListener("click", function () {

        const bno = '${boardDTO.bno}'
        const replyer = document.querySelector("input[name='replyer']").value
        const reply = document.querySelector("input[name='reply']").value

        if (oper === 'add') {
            // console.log(bno, replyer, reply)

            const replyObj = {bno, replyer, reply} // {bno:bno, replyer:replyer, reply:reply}
            console.log(replyObj)
            // addReply(replyObj).then(result => {
            //     getList() //비동기 호출 2번 일어남!
            //     modalDiv.modal('hide') //입력 후 모달창 사라지게
            //     document.querySelector("input[name='replyer']").value = "" //모달창 입력값 없애기
            //     document.querySelector("input[name='reply']").value = ""
            // })
        }

    }, false)

    //수정/삭제 dom
    const modModal = $("#modal-lg")
    const modReplyer = document.querySelector("input[name='replyerMod']")
    const modReply = document.querySelector("input[name='replyMod']")
    const modRno = document.querySelector("input[name='rno']")

    document.querySelector(".direct-chat-messages").addEventListener("click", (e) => { // 안에있는 애들은 동적으로 처리된 데이터. 얘는 원래 있었던 애라 얘한테 이벤트 걸어줌

        const target = e.target
        const bno = '${boardDTO.bno}'

        if(target.matches(".direct-chat-text")) {
            //data-속성으로 뽑아온 값을 추출
            const rno = target.getAttribute("data-rno")
            const replyer = target.getAttribute("data-replyer")
            const reply = target.innerHTML
            console.log(rno, replyer, reply, bno)

            modRno.value = rno
            modReply.value = reply
            modReplyer.value = replyer

            //Remove 버튼 눌렀을 때 rno 값 받아오기
            document.querySelector(".btnRem").setAttribute("data-rno", rno)

            modModal.modal('show')
        }
    }, false)

    document.querySelector(".btnRem").addEventListener("click", (e) => {
        const rno = e.target.getAttribute("data-rno")
        //alert(rno) //클릭하면 rno 나오게 // 대신 axios 사용
        removeReply(rno).then(result => {
            getList()
            modModal.modal('hide')
        })
    }, false)

    document.querySelector(".btnModReply").addEventListener("click", (e) => {

        const replyObj = {rno: modRno.value , reply: modReply.value} //이미 값을 물고 있으므로 얘네만 데려오면 됨

        console.log(replyObj)

        modifyReply(replyObj).then(result => {
            getList()
            modModal.modal('hide')
        })
    }, false)

</script>

</body>
</html>