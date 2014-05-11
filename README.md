XToast
====
A simple custom toast(Simplified version of SuperToasts).

### Usage

#### Basic
```java
XToast.create(Context, "Text to show").show();
```

#### Custom
```java
XToast.create(Context, "Text to show").withTextSize(20).withDuration(2000).show();
```

All configurations are in the form of withXxx() methods.

**It now supports the following configurations:**
##### Text size/color
```java
withTextSize(int)
withTextColor(int)
```
    
##### Duration
```java
withDuration(int)
```
There are several build-in duration in XToast.Duration.

##### Background color/resource
```java
withBackgroundColor(int)
withBackgroundResource(int)
```

##### Animation
```java
withAnimation(int)
```
There are several built-in animations in XToast.Anim.

##### Gravity and offset
```java
withGravity(int, int, int)
```

##### Cover previous toast
```java
withCover(boolean)
```
Cancel all the other toasts before this one shows.

##### Integrate with a button(text/event)
```java
withButton(Charsequence, XToast.ButtonClickListener)
```

