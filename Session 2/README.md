Android - Session Two
=====================
Custom Views
------------

[Presentation](https://docs.google.com/presentation/d/14OhEfCL41m8AU0aFXcnwM_esWfwpmuMeUUQC7u2nTaQ/edit?usp=sharing)

Objectives
----------
- understand basic View properties, methods
- extend existing widgets
- understand Canvas operations
- create completely custom component from scratch
- learn how to make custom views interactive

Suggested Further Exercises
---------------------------
1. Complete Split The Game.
    - Suggestions:
        - Start from the existing code in SplitGameView.java.
        - Read and understand the comments in that file - or ask!
        - Display "Game Over" when a ball touches the bottom of the screen.
        - Keep track of total time played, display as score.
        - Remove balls that are too small to tap reliably.
    - Hints:
        - Use a boolean (i.e. flag) to designate whether the game is active (if false - player has lost).
        - If a ball collides with the ground, set flag to false.
        - If flag is true, update game as usual; otherwise, use Canvas.drawText(..) to display "Game Over."
        - In onTouchEvent(..), if the flag is true, reset the game and set it back to true.
2. Create a custom radial menu.
    - Suggestions:
        - Extend base View class.
        - Use Canvas.drawCircle(..) and Canvas.drawArc(..) in onDraw(..).
        - Use multiple Paint objects, play with their color, style and stroke options.
        - Use a [GestureDetector](http://developer.android.com/training/gestures/detector.html) to detect swipes, etc.
3. Create a drawing app.
    - Suggestions:
        - Use multiple Views in your layout; several standard widgets (e.g. SeekBars) and your custom View.
        - Your custom view (let's call it "CanvasView") should respond to touch events, let user draw.
        - Your standard widgets (say, SeekBars) should be used to configure the drawing, e.g. width of stroke.
    - Hints:
        - In your layout file, give each View (including your custom one) an id
            - e.g.    android:id="@+id/canvasView"
        - In your Activity, retrieve all your Views. Attach necessary listeners, have them interact appropriately.
            - e.g.    CanvasView myCustomCanvasView = (CanvasView) findViewById(R.id.canvasView);
        - In your CanvasView, in onDraw(..), do not use Canvas.drawColor(..) or similar, such that the drawing persists.
        - In your CanvasView, in onTouchEvent(), store the positions of touch events you want to draw; draw them in onDraw(..)