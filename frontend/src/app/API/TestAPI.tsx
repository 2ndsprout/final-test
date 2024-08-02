import { getAPI } from "./AxiosAPI";

export const TestAPI = getAPI();

interface ArticleProps {
    id?: number;
    title?: string;
    content?: string;
}

export const getArticle = async (id: number) => {
    const response = await TestAPI.get('/api/article', {headers: {'ArticleId': id}});
    return response.data;
}

export const postArticle = async (article: ArticleProps) => {
    const response = await TestAPI.post('/api/article', article);
    return response.data;
}

export const putArticle = async (article: ArticleProps) => {
    const response = await TestAPI.put('/api/article', article);
    return response.data;
}

export const deleteArticle = async (id: number) => {
    await TestAPI.delete('/api/article', {headers: {'ArticleId': id}});
}

export const getArticlePage = async (page: number) => {
    const response = await TestAPI.get('/api/article/list', {headers: {'Page': page}});
    return response.data;
}