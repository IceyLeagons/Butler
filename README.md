# 💁‍♂ Butler
*Spend less ⏳ time for way more, ⚖ and manage it all in one space.*
### ✅ Made in 48 hours for the Junction 2022 event at Espoo.
## 🤔 What even is a Butler?
> 🤓 According to Google, butlers are the chief manservant of a house. However, in our case, it -- of course -- is not.

Butler is our take on the "do it all on one-site" ordeal. It uses APIs from big companies, like Google and Microsoft and, for instance, syncs their events --- that you've saved there --- into one, easily understandable calendar. The project was made for the 👽 [Year 2222 track, from Eiger](https://www.junction2022.com/challenges-new/eiger), since it felt like their challenge offered us the most leeway. Since their challenge was set in the far future, we took matters into our own hands and created some "imaginary apis" that would -- in this distant new world -- handle interactions with your home or, for instance, bank account.
## 😅 Why create Butler?
We use many sites for different purposes, but never one site. It is neither ⚡ energy-efficient to load 15 different sites, just to get your news, the current weather, and a place to go, nor is it good for your 🤒 mental sanity to spend stupendous amounts of time staring at your phone. Imagine this daily routine: you wake up, grab your phone, spend 5 minutes looking at 4 different apps to get up-to-date, then go on with your day --- oh wait, that's how it is nowadays...

We, as students, with how ⌚ time-constrained we already are, -- especially in the mornings, -- cannot afford to spend so much time with such unnecessary things, so we made this **ONE** (1) site, that'll help us keep in contact with the outside world, without ever stepping a foot outside.

We also believe that the 🏔️ "*rift*" that has formed between big tech companies nowadays, will only widen as time goes on, so bridging them as soon as we can is the only way to 🛸 "save Earth from its impending *tech doom*."
## 📖 Why use Butler?
Most current solutions to these problems, are either non-existent, or just too obscure to even hear about. I mean, a neat homepage for your browser with fresh information sounds good, right? Why pass up the chance?
## 🤞 Features
#### Butler currently sports the following features:
- ☁ weather information based on current location
- 📰 news from subscribed sources**
- 💡 recommendations/ideas for activities
- 👀 YouTube video recommendations
- 🥳 search concerts from most listened to artists on Spotify
- 🔌 toggle/setup smart appliances***

** requires the news source to have an [RSS](https://en.wikipedia.org/wiki/RSS) link.

*** stub function, does not actually change anything. This is one of those "made in the future apis."
## 🚗 Future
In the future we plan to add a lot more integrations with big tech and implement the current stubs whenever we get access to an API that can do those things.
## 📚 Backend technologies
- ☕ [Kotlin](https://kotlinlang.org/)
- 🌿 [Spring](https://spring.io/)
- 🎶 [Spotify](https://open.spotify.com/)
- ☁ [Open-Meteo](https://open-meteo.com)
- 📶 [Wigle](https://wigle.net/)
- 🗺️ [Google Maps](https://maps.google.com/)
- 📅 [Google Calendar](https://calendar.google.com/)
- 📜 [Microsoft Graph](https://learn.microsoft.com/en-us/graph/overview)
- 🎭 [YouTube](https://youtube.com/)
## 🙈 Frontend technologies
- 📄 [Svelte](https://svelte.dev/)
- 📰 [TypeScript](https://www.typescriptlang.org/)
- 📆 [FullCalendar](https://fullcalendar.io/)
- 🕸️ [page.js](https://visionmedia.github.io/page.js/)
- 🎨 [Vite](https://vitejs.dev/)
## 🖥️ Requirements
At least 128Mb of RAM and a CPU fast enough to run Java -- so around 2Ghz -- will be fine --- *I guess?*

You'll also need a running instance of [Gatekeeper](https://github.com/IceyLeagons/Gatekeeper) and all the tokens that it requires.
## What is 💂 Gatekeeper, and why is it necessary?
Gatekeeper is a central OAuth management platform we made specifically for this project. Its architecture provides us with an easy way to later implement more providers. It is necessary, because it handles identification.
## 👷‍♂️ Compiling & Executing
Compiling is as standard as it could be for a Gradle project:

    ./gradlew build

and execution is just as easy as compilation, with it being:

    ./gradlew bootRun

### ☢️ BEFORE YOU RUN!
Make sure that in your `application.properties` file the following values are set:

    gatekeeper.host=
    gatekeeper.redirect_uri=
    gatekeeper.client_id=
    gatekeeper.client_secret=
    seatgeek.api.client_id=
    seatgeek.api.client_secret=
    google.api.places.token=
    wigle.api.token=

A free Google account should be enough to support low-traffic situations.
### 🤔 What if I don't have keys for some of these?
❗ If the one you don't have access to is Gatekeeper, then you should give up on running your own instance. Then, you're better off just using our -- hopefully still running -- demo.

👌 If it is either Wigle, Google or Seatgeek, then except some Spring `@Value` errors thrown at you, you should be good to go.
## 🤗 A big thanks to Google and Wolt for providing free access to their APIs during development.