SnowShoe Stamp SDK
===================

The [SnowShoe Stamp](http://www.snowshoestamp.com) is an authentication tool for smartphones.

<p align="center" >
  <img src="https://beta.snowshoestamp.com/static/api/img/stamp.gif" alt="SnowShoe" title="SnowShoe" width="160" height="284">
</p>

## Installation

SnowShoe Stamp SDK is available through [jCenter](https://bintray.com/bintray/jcenter). To install
it, add the following line to your projects `build.gradle`repositories:

```groovy
maven {
        url  "https://dl.bintray.com/snowshoestamp/maven" 
    }
```
and add the following line to your modules `build.gradle`dependencies:
```groovy
implementation 'com.mattluedke:snowshoelib:3.0.3'
```

## Usage

1. To get the app running, you will need to create an app on our site. Go to https://app.snowshoestamp.com/ and Sign In if you have an account or sign up if you don't have one. Once you are logged in, click “New App” to create a new one.

2. After you have created the new application look at it's settings and you will find 'API Key 1' and 'API Key 2'. These can both be used as the api key you will need later when setting up the app.

The core piece of this library is the `SnowShoeView`, a subclass of `View` that automatically detects stamps and handles the API query.

You can add one to your layout xml:

```xml
<com.mattluedke.snowshoelib.SnowShoeView
  ...
/>
```

Then, assign your api key from the app that you would like to set as the one receiving calls from this app:

```java
snowShoeView.setApiKey("YOUR_API_KEY");
```

Then, implement `OnStampListener`, which will be notified when a stamp request is made to the API and when a result comes back:

```java
public interface OnStampListener {
  void onStampRequestMade();
  void onStampResult(StampResult result);
}
```

Then assign the listener to the `SnowShoeView`:

```java
snowShoeView.setOnStampListener(listener);
```

# More info

- This view can be used as the main view for a window or an overlay for stamping on.
- For more info on how to use our product visit: 
    https://snowshoe.readme.io/v3.0/docs
