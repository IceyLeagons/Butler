export default async function getRandomFact(): Promise<String> {
    return new Promise(async (resolve) => {
        let resp = await fetch("https://uselessfacts.jsph.pl/random.json?language=en");
        let json = await resp.json();
        let fact = json["text"];


        resolve(fact.charAt(0).toLowerCase() + fact.slice(1));
    });
}