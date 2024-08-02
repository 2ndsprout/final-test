import { useState } from "react";

interface PageInterface {
    children: React.ReactNode;
    className?: string;
  }
  
  export default function Main(props: Readonly<PageInterface>) {
    const { children, className } = props;

    const [temp, setTemp] = useState(0);
  // 필요한 요청 파라미터를 조립하여 api로 부터 데이터 받아와 업데이트하는 함수
  const getWeather = () => {
      const key =
          "paJ%2BM8y80vWX8Gu5RWTDurJ0y5rQCX4tjEwLh0F%2FwfUABNbw%2BV2iJD%2FBahqq08K%2BvzgPyAU0GFZ84LmVfEDPgA%3D%3D";

      const dd = new Date();
      const y = dd.getFullYear();
      const m = ("0" + (dd.getMonth() + 1)).slice(-2);
      const d = ("0" + dd.getDate()).slice(-2);
      const ds = y + m + d;

      const dd2 = new Date();
      const h = ("0" + dd2.getHours()).slice(-2);
      const ts = `${h}00`;

      var url =
          "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey= " +
          key +
          "&pageNo=1&numOfRows=1000&dataType=JSON" +
          "&base_date=" +
          ds +
          "&base_time=" +
          ts +
          "&nx=67&ny=100";

      fetch(url)
          .then((res) => res.json())
          .then((data) => {
              console.log(data.response.body.items.item);
              const itemArr = data.response.body.items.item;
              const result = {};
              itemArr.forEach((item:any) => {
                  if (item.category === "T1H") {
                      setTemp(item.obsrValue);
                  }
              });
          })
          .catch((err) => console.log(err));
  };
  
    return (
      <main id='main' className={'bg-white min-h-screen w-[400px] sm:w-[1903px] flex flex-col relative ' + className}>
        <div className="gap-64 w-full h-[70px] flex bg-gray-500 text-black">
          <a href="/" className="ml-16 h-full content-center w-[100px]">
            게시물 목록
          </a>
          <a href="/" className="ml-16 h-full content-center w-[100px]">
            현재기온 : {temp}도
          </a>
          <a href="/article/create" className="h-full content-center w-[100px]">
            게시물 작성
          </a>
        </div>
        <div className="flex flex-col">
          {children}
        </div>
      </main>
    );
  }
  