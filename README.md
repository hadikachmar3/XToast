A simple custom toast(Simplified version of SuperToasts).

# Usage

## Basic
    XToast.create(Context, "Text to show").show();

## Custom
    XToast.create(Context, "Text to show").withTextSize(20).withDuration(2000).show();
All configurations are in the form of withXxx() methods.

**It now supports the following configurations:**
### Text size/color
    withTextSize(int)
    withTextColor(int)
    
### Duration
    withDuration(int)
There are several build-in duration in XToast.Duration.

### Background color/resource
    withBackgroundColor(int)
    withBackgroundResource(int)

### Animation
    withAnimation(int)
There are several built-in animations in XToast.Anim.

### Gravity and offset
    withGravity(int, int, int)

### Cover previous toast
    withCover(boolean)
Cancel all the other toasts before this one shows.

### Integrate with a button(text/event)
    withButton(Charsequence, XToast.ButtonClickListener)

