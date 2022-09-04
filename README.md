<div id="top"></div>


<!-- PROJECT LOGO -->
<br />
<div align="center">

  <h3 align="center">Tracker of the Rings</h3>

  <p align="center">
    An app to track campus buses which can be scaled to any organization
    <br />
    <br />
    ·
    <a href="https://github.com/ali-tunahan/TrackerOfTheRings/issues">Report Bug</a>
    ·
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Project

Locator of the Rings is an app to track rings and show Ring stops to users. The main objective of the project is to help Bilkenters not miss their lectures by showing them where the Rings and Ring stops are at and estimated time of arrivals. Every time we missed a ring, we wished to have an app to show us the estimated time of arrival of the Rings. There probably are more Bilkenters who wish to have a similar app. The main goal of the project is to provide this app.

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With
* [![Android][Android.io]][Android-url]

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* Minimum Android 8.0 (API level 26)
* implementation platform('com.google.firebase:firebase-bom:29.3.1')
* implementation 'com.google.firebase:firebase-database'
* implementation 'androidx.appcompat:appcompat:1.4.1'
* implementation 'com.google.android.material:material:1.5.0'
* implementation 'com.google.android.gms:play-services-maps:18.0.2'
* implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
* implementation 'com.google.android.gms:play-services-location:19.0.1'
* implementation 'com.google.firebase:firebase-database:20.0.4'
* implementation 'androidx.legacy:legacy-support-v4:1.0.0'
* testImplementation 'junit:junit:4.+'
* androidTestImplementation 'androidx.test.ext:junit:1.1.3'
* androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
* implementation 'androidx.navigation:navigation-fragment:2.4.2'
* implementation 'androidx.navigation:navigation-ui:2.4.2'

### Installation

1. Run the app-debug.apk file from any Android device with GPS and at least Android 8.0

*OR

1. Get a free API Key at [https://developers.google.com/maps](https://developers.google.com/maps)
2. Clone the repo

   ```sh
   git clone https://github.com/ali-tunahan/TrackerOfTheRings.git
   ```

3. Insert your own API key as a local variable to MAPS_API_KEY in `local.properties`

   ```dsl
   MAPS_API_KEY = ENTER YOUR API
   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage
The homepage of the app welcomes users with three different options to choose from. These options are driver, user and company.

If you select the “Driver” button you are led to a login screen where you cant enter your user name, password and company ID. Then the driver can press the start button and choose a route to follow. After this, the driver's location will be shared with the driver's company and users.

If you select the "User" button then you should enter your company ID. After this, you will be led to a map where you can see yoruself and the stops and drivers that correspond to your company ID.

If you select the "Company" button you are led to a login screen where you cant enter your user name, password and company ID. From the company homepage you can add new driver accounts, oversee your current driver fleet. You can also add, remove, edit stops and routes.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Ali Tunahan Işık - [LinkedIn](https://www.linkedin.com/in/ali-tunahan-işık-921a23230/)

E-mail: tunahan0735@gmail.com

Alternative: tunahan.isik@ug.bilkent.edu.tr


Umut Bora Çakmak - [LinkedIn](https://www.linkedin.com/in/umut-bora-çakmak-a0931a232/)

E-mail: bora.umut1@hotmail.com

Enis Kerem Çakmak - [LinkedIn](https://www.linkedin.com/in/enis-kerem-çakmak-a17947219/)

E-mail: -

Perit Dinçer - [LinkedIn](https://www.linkedin.com/in/perit-dinçer-5a17b5238/)

E-mail: peritdincer@gmail.com


Project Link: [https://github.com/ali-tunahan/Tracker_Of_The_Rings](https://github.com/ali-tunahan/TrackerOfTheRings)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments
These resources helped me for this project

* [Firebase documentation](https://firebase.google.com/docs)
* [Img Shields](https://shields.io)
* [Readme Template](https://github.com/othneildrew/Best-README-Template)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
[Android.io]: https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white
[Android-url]: https://www.android.com
