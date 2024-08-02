'use client'

import { deleteArticle, getArticle, putArticle } from "@/app/API/TestAPI";
import Main from "@/app/Global/Layout";
import { getDateTimeFormat } from "@/app/Global/Method";
import { useParams } from "next/navigation";
import { use, useEffect, useState } from "react";

export default function Page() {

    const params = useParams();
    const articleId = Number(params?.id);
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [createDate, setCreateDate] = useState('');
    const [modifyDate, setModifyDate] = useState('');
    const [disabled, setDisabled] = useState(false);

    useEffect(() => {
        getArticle(articleId)
            .then(r => {
                setTitle(r.title);
                setContent(r.content);
                setCreateDate(getDateTimeFormat(r.createDate));
                setModifyDate(r.modifyDate !== null ? getDateTimeFormat(r.modifyDate) : '');
            })
            .catch(e => console.log(e));
    });

    function updateArticles() {
        putArticle({ id: articleId, title: title, content: content })
            .then(r => {
                alert("게시물이 수정되었습니다.");
                console.log(r);
                setDisabled(false);
                setTitle(r.title);
                setContent(r.content);
                setCreateDate(getDateTimeFormat(r.createDate));
                setModifyDate(getDateTimeFormat(r.modifyDate));
            })
            .catch(error => {
                console.error('Error updating article:', error);
                setDisabled(false);
            });
    }

    async function deleteArticles() {
        try {
            await deleteArticle(articleId);
            console.log("Article deleted successfully");
            alert("게시물이 삭제되었습니다.");
            window.location.href = "/";
        } catch (error) {
            console.error("Error deleting article:", error);
        }
    }
    


    return (
        <Main className="bg-white text-black flex flex-col items-center">
            <div className="mt-16 items-center h-[700px] w-[700px] flex flex-col gap-5">
                {disabled ? <span className="text-3xl font-bold">게시물 수정</span> : 
                <span className="text-3xl font-bold">게시물 상세보기</span>}
                <input
                    type="text"
                    defaultValue={title}
                    onChange={e => {
                        setTitle(e.target.value);
                        console.log(title);
                    }}
                    disabled={!disabled}
                    className={`ml-3 p-2.5 rounded-xl w-full border-2 border-black h-[50px]` + (!disabled ? " bg-gray-300" : "")}
                />
                <textarea
                    name="content"
                    id="content"
                    defaultValue={content}
                    onChange={e => setContent(e.target.value)}
                    disabled={!disabled}
                    className={`ml-3 p-2.5 rounded-xl resize-none border-2 border-black w-full h-[300px]` + (!disabled ? " bg-gray-300" : "")}
                />
                <span className="rounded-xl w-full border-2 border-black h-[50px] text-2xl font-bold">작성일: {createDate}</span>
                <span className="rounded-xl w-full border-2 border-black h-[50px] text-2xl font-bold">수정일: {modifyDate}</span>
                {disabled ?
                    <button className="w-[100px] h-[50px] bg-green-500 rounded-xl" onClick={() => updateArticles()}>
                        수정 완료
                    </button> :
                    <div><button className="w-[100px] h-[50px] bg-green-500 rounded-xl" onClick={() => setDisabled(true)}>
                        수정
                    </button>
                    <button className="w-[100px] h-[50px] bg-red-500 rounded-xl" onClick={() => deleteArticles()}>
                        삭제
                    </button>
                    </div>}
            </div>
        </Main>

    );
}