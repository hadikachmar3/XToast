XToast
====
A simple android custom toast(Simplified version of SuperToasts).

## Usage

- Basic
```java
XToast.create(Context, "Text to show").show();
```

- Custom
```java
XToast.create(Context, "Text to show").withTextSize(20).withDuration(2000).show();
```

All configurations are in the form of withXxx() methods.

----

**It now supports the following configurations:**

## 1. Text size/color
```java
withTextSize(int)
withTextColor(int)
```

## 2. Duration
```java
withDuration(int)
```
There are several build-in duration in `XToast.Duration`.

## 3. Background color/resource
```java
withBackgroundColor(int)
withBackgroundResource(int)
```

## 4. Animation
```java
withAnimation(int)
```
There are several built-in animations in `XToast.Anim`.

## 5. Gravity and offset
```java
withGravity(int, int, int)
```

## 6. Cover previous toast
```java
withCover(boolean)
```
Cancel all the other toasts before this one shows.

## 7. Button integration(text/icon/event)
```java
withButton(Charsequence, Drawable, XToast.ButtonClickListener)
```

## 8. Potision relative to a specified view
```java
withPosition(View, int, int, int)
```
See position options in `XToast.Position`.
