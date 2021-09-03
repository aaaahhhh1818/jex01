
//비동기를 동기화처럼 처리되려면 async 사용
async function doA() { //함수로 - 선언문

    console.log("doA.................")

    const response = await axios.get("/replies") //결과가 나올 때 까지 기다렸다가 결과 나오면 response에 할당해라
    const data = response.data
    console.log("doA..data", data)
    return data //doA를 실행하면 배열이 나옴 -> 리턴을 받는 방식
}

const doB = (fn) => { //변수 - 선언식
    console.log("doB..................")
    try {
        axios.get('/replies').then(response => { //결과가 비동기 -> then 처리 해줘야 함
            console.log(response)
            const arr = response.data // fn은 함수여서 arr에 담아줌
            fn(arr) //함수를 호출해주고 이 데이터가 있으면 이 함수를 실행해줘? -> 콜백방식
        })
    }catch (error) {
        console.log(error)
    }
}

async function doC(obj) { //post 방식일 때 파라미터 넣어주기

    const response = await axios.post("/replies", obj)

    return response.data //항상 promise 반환함. 오면 이 데이터 리턴해줄껀데 온제올지 모르겠넹? => 비동기

}

const doD = async (rno) => {

    const response = await axios.delete(`/replies/${rno}`) //js파일은 벡틱 자유롭게 사용가능

    return response.data

}

const doE = async (reply) => {

    const response = await axios.put(`/relies/${reply.rno}`, reply)

    return response.data
}