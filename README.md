# Movies App
Sample Android app that displays a list of movies using [iTunes search api](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/#searching)

Libraries and tools:

- [AndroidX](https://developer.android.com/jetpack/androidx)
- [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Retrofit 2](http://square.github.io/retrofit/)
- [Dagger 2](http://google.github.io/dagger/)
- [Room](https://developer.android.com/training/data-storage/room)
- [Butterknife](https://github.com/JakeWharton/butterknife)
- [Glide](https://github.com/bumptech/glide)
- [ExoPlayer](https://exoplayer.dev/hello-world.html)

## Requirements

- JDK 1.8
- [Android SDK](https://developer.android.com/sdk/index.html)
- Latest Android SDK Tools and build tools

## Architecture

The architecture of this app is based on the [MVP](https://en.wikipedia.org/wiki/Model–view–presenter) (Model View Presenter) pattern

#### How to implement a new screen following MVP

Imagine you have to implement a sign in screen.

1. Create a new package under `ui` called `signin`
2. Create a new Activity called `SignInActivity` class that extends `BaseActivity`. You could also use a Fragment
3. Define the view and presenter interfaces that your Activity/Fragment and Presenter is going to implement. Create a new interface called `SignInContract`, inside create another interface called `View` that extends `BaseView` and another one called `Presenter` that extends `BasePresenter<View>`. Add the methods that you think will be necessary on both interfaces, e.g. `showSignInSuccessful()` for the `View` and `signIn(String email)` for the `Presenter`.
    Code should look like this:

    ```java
    interface SignInContract {
        interface View extends BaseView {
            void showSignInSuccessful();
        }

        interface Presenter extends BasePresenter<View> {
            void signIn(String email);
        }
    }
    ```
4. Create a `SignInPresenter` class that extends `BasePresenter<SignInContract.View>` and implements `SignInContract.Presenter`
5. Implement the methods in `SignInPresenter` that your Activity requires to perform the necessary actions, e.g. `signIn(String email)`. Once the sign in action finishes you should call `getView().showSignInSuccessful()`
6. Make your `SignInActivity` implement `SignInContract.View` and implement the required methods like `showSignInSuccessful()`
7. In your activity, inject a new instance of `SignInPresenter` and call `super.attachPresenter(presenter)` from `onCreate`. This will allow your presenter to have an instance of your activity and it would also let your Presenter have a lifecycle awareness, this way you can override `onStart`, `onResume`, `onPause`, `onStop` and `onDestroy` methods on your Presenter. Also, set up a click listener in your button that calls `presenter.signIn(email)`

## License

```
    Copyright 2019 Kenneth Lopez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```