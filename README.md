# Validation Animation UI 

A sample typing animation with validation regex check.

## Installation

Add Validateionanim library into your project module, so you have below line into your project's build. gradle file.

```bash
implementation project(path: ':validationanim')
```

## Usage

Add ```com.play.validationanim.ValidationAnimView``` view where you want to show animation view. you can also bind edittext id by using ```editTextRef``` and pattern to match the validation using ```patternToMatch``` attributes.

## 1st method

Add following code into your layout.xml 
```xml
<LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:background="@drawable/text_feild_bg"
        android:orientation="horizontal"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView">

        <EditText
            android:id="@+id/input_field1"
            android:layout_width="wrap_content"
            android:layout_height="50dp"
            android:background="@android:color/transparent"
            android:ems="10" />

        <com.play.validationanim.ValidationAnimView
            android:id="@+id/valid_anim_view"
            android:layout_width="30dp"
            android:layout_height="30dp"
            android:layout_gravity="center"
            android:focusable="false"
            android:focusableInTouchMode="false"
            android:focusedByDefault="false"
            app:editTextRef="@+id/input_field1"
            app:patternToMatch="@string/sample" />
    </LinearLayout>
```

add string sample into your string.xml file. this sample string pick from [Patterns.EMAIL_ADDRESS](https://developer.android.com/reference/android/util/Patterns#EMAIL_ADDRESS)
```xml
 <string name="sample">[a-zA-Z0-9\+\.\_\%\-\+]{1,256}\@[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{0,25})+</string>
```
Run the sample for better idea.

## Second way to implement.
Second way you can also bind edittext and patterns from the code. It has a function ```setUpWithEditText```, which accept edittext and regex string as a parameter.
```java
 binding.validAnimView.setUpWithEditText(binding.inputField1, Patterns.EMAIL_ADDRESS.toString())
```
## Manage by your own.
you can also handle animation by setting property by using following functions -
``` java
validAnimView.typingState = true //when show a typing animation
validAnimView.validState = true //when show validation animation
validAnimView.invalidState = true //when show invalid animation
validAnimView.clearState = true //when want to clear the animation
```
## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update the tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
