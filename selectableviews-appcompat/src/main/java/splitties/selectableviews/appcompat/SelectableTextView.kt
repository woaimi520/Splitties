/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.selectableviews.appcompat

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import splitties.resources.styledDrawable

class SelectableTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)

    var foregroundSelector: Drawable? = null
        set(value) {
            field?.callback = null
            field = value
            value?.callback = this
            setWillNotDraw(value === null)
        }

    init {
        foregroundSelector = styledDrawable(android.R.attr.selectableItemBackground)
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        foregroundSelector?.state = drawableState
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        foregroundSelector?.setBounds(0, 0, w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        foregroundSelector?.draw(canvas)
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        foregroundSelector?.jumpToCurrentState()
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return who === foregroundSelector || super.verifyDrawable(who)
    }

    @TargetApi(LOLLIPOP)
    override fun dispatchDrawableHotspotChanged(x: Float, y: Float) {
        super.dispatchDrawableHotspotChanged(x, y)
        foregroundSelector?.setHotspot(x, y)
    }
}
