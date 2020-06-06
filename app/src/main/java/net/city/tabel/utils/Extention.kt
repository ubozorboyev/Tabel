package net.city.tabel.utils

import android.animation.Animator
import android.view.ViewPropertyAnimator


fun ViewPropertyAnimator.setOnFinishListener(f:()->Unit): ViewPropertyAnimator {

    setListener(object : Animator.AnimatorListener{
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            f()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }
    })
    return this
}
