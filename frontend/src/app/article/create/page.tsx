'use client'

import { postArticle, putArticle } from "@/app/API/TestAPI";
import Main from "@/app/Global/Layout";
import { getDateTimeFormat } from "@/app/Global/Method";
import { useParams, useRouter } from "next/navigation";
import { useState } from "react";

export default function Page() {

    const router = useRouter();
    const [articleId, setArticleId] = useState(0);
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [createDate, setCreateDate] = useState('');
    const [changer, setChanger] = useState(false);
    const [disabled, setDisabled] = useState(false);

    function postArticles() {
        postArticle({ title: title, content: content })
            .then(r => {
                alert('게시물이 작성되었습니다.');
                window.location.href = `/`;
            })
            .catch(e => console.log(e));
    }

    function roadArticle() {
        router.push(`/article/modify/${articleId}`);
    }


    return (
        <Main className="bg-white text-black flex flex-col items-center">
            <div className="mt-16 items-center h-[700px] w-[700px] flex flex-col gap-5">
                <span className="text-3xl font-bold">게시물 작성</span>
                <input
                    type="text"
                    value={title}
                    onChange={e => {
                        setTitle(e.target.value);
                        console.log(title);
                    }}
                    disabled={disabled}
                    className="ml-3 p-2.5 rounded-xl w-full border-2 border-black h-[50px]"
                />
                <textarea
                    name="content"
                    id="content"
                    value={content}
                    disabled={disabled}
                    onChange={e => setContent(e.target.value)}
                    className="ml-3 p-2.5 rounded-xl resize-none border-2 border-black w-full h-[300px]"
                />
                <span className="rounded-xl w-full border-2 border-black h-[50px] content-center text-2xl font-bold">작성일: {createDate}</span>
                {!changer ?
                    <button className="w-[100px] h-[50px] bg-green-500 rounded-xl" onClick={() => postArticles()}>
                        게시
                    </button> :
                    <button className="w-[100px] h-[50px] bg-green-500 rounded-xl" onClick={roadArticle}>
                        수정 및 삭제
                    </button>}
            </div>
        </Main>

    );
}