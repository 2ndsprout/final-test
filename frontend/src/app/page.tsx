'use client'

import { useEffect, useState } from "react";
import { getArticlePage } from "./API/TestAPI";
import Main from "./Global/Layout";

export default function Page() {
  const [articleList, setArticleList] = useState<any[]>([]);
  const [page, setPage] = useState(0);
  const [maxPage, setMaxPage] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [showLoading, setShowLoading] = useState(false); // 로딩 메시지 표시 상태 추가

  useEffect(() => {
    const loadArticles = async () => {
      setIsLoading(true);
      setShowLoading(true); // 로딩 메시지 표시
      try {
        const r = await getArticlePage(page);
        setArticleList(prevList => [...prevList, ...r.content]);
        setMaxPage(r.totalPages);
      } catch (e) {
        console.error(e);
      } finally {
        // 데이터 로딩이 끝난 후 3초간 로딩 메시지 표시
        setTimeout(() => {
          setIsLoading(false);
          setShowLoading(false);
        }, 3000);
      }
    };

    loadArticles();
  }, [page]);

  useEffect(() => {
    const handleScroll = () => {
      const scrollLocation = window.scrollY + window.innerHeight;
      const fullHeight = document.documentElement.scrollHeight;

      if (scrollLocation >= fullHeight - 100 && page < maxPage - 1) {
        setPage(prevPage => prevPage + 1);
      }
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, [page, maxPage]);

  return (
    <Main className="bg-white text-black">
      <div className="mt-16 h-full flex flex-col items-center justify-center">
        <div className="flex flex-wrap justify-center">
          {articleList.map((article, index) => (
            <a href={'/article/modify/' + article.id} key={index} className='mr-4 mb-4'>
              <div className='sm:w-[500px] w-[1000px] h-[500px] text-xl flex items-center justify-center hover:font-bold hover:text-blue-500 p-4 hover:border border-gray-500 bg-gray-300 hover:cursor-pointer'>
                <label className='text-center'>{article?.title ? article?.title : '제목 없음'}</label>
              </div>
            </a>
          ))}
        </div>
        {showLoading && (
          <div className="mt-16 fixed text-3xl">불러오는 중...</div>
        )}
      </div>
    </Main>
  );
}
