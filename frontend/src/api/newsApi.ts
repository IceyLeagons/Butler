type NewsItem = {
    title: string;
    link: string;
    publish: Date;
    content: string;
}

type News = {
    title: string;
    link: string;
    imageUrl: string;
    items: NewsItem[];
};

let items = [];
for (let i = 0; i < 20; i++) {
    items.push(
        {
            title: "News 1",
            link: "link",
            publish: new Date(55345662345),
            content: "Contentiowerivunwer oiweruvniowueroniuweroviuwerhj"
        }
    );
}

const news: News[] = [
    {
        title: "Title",
        link: "link",
        imageUrl: "https://szeged.hu/image/logo/szegedhu-logo-desktop.svg",
        items: [
            {
                title: "News 1",
                link: "link",
                publish: new Date(55345662345),
                content: " oiweruvniowueroniuweroviuwerhj"
            },
            ...items
        ]
    }
]

function mapNewsItems(array): NewsItem[] {
    let resp: NewsItem[] = []
    for (let i = 0; i < array.length; i++) {
        let item = array[i];
        resp.push({
            title: item["title"],
            link: item["link"],
            publish: new Date(item["publish"]),
            content: item["content"]
        });
    }

    return resp;
}

function mapNews(json): News {
    return {
        title: json["title"],
        link: json["link"],
        imageUrl: json["image_url"],
        items: mapNewsItems(json["items"])
    }
}

export async function getNews(): Promise<News[]> {
    return new Promise(async (resolve) => {
        resolve(news);
    });
}
export type {NewsItem, News}