
## 一：开始使用
### 配置Databinding并转化布局
- 在 module 级别的 build.gradle 的 android 框中添加如下配置，然后 Sync

```
android {
    ...
    dataBinding {
        enabled = true
    }
}

```
**注意：**如果该 module 依赖了  libraries， 且 libraries 中使用 databinding，那么在 该 module 中也必须配置开启 dataBinding，就算不使用也需要开启。

**补充：**：
如果用` kotlin` ,最好在 `build.gradle` 的最上面加上

`'apply plugin: 'kotlin-kapt'`
不然布局里报红，看起来贼烦。

---------

- 转化 layout：现在你可以转化 layout 了



> a. Wrap your layout with a `<layout>` tag
> 用`<layout>` 标签包裹布局
> b. Add layout variables (optional) 
> 添加布局变量（可选）
c. Add layout expressions (optional)
添加布局表达式（可选）
>

- 不过 Android Studio 提供了一种自动完成此操作的方法。如下
![](https://codelabs.developers.google.com/codelabs/android-databinding/img/59e3fa032d975200.png)


- 转化好后应该是这样

```
<layout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools">
   <data>

   </data>
   <android.support.constraint.ConstraintLayout
           android:layout_width="match_parent"
           android:layout_height="match_parent">

       <TextView
...
```
### 建立你的第一个布局表达式
- 在  `<data> `标签中建立两个`string`类型的布局变量。

```    
    <data>
        <variable name="name" type="String"/>
        <variable name="lastName" type="String"/>
    </data>
```
- 在下方的找到id 为 plain_name 的Textview ，用布局表达式为其设置text 值。

```
<TextView
          android:id="@+id/plain_name"
          android:text="@{name}"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content" />

```
布局表达式用@ 开头，并用大括号把变量包裹起来，如`@{name}`
- 同样的方式设置 id 为 plain_lastName 的Textview


```
<TextView
          android:id="@+id/plain_lastName"
          android:text="@{lastName}"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content" />

```
接下来我们需要去修改 Activity 中的代码了
### 改变 Activity 绑定布局方式 并移除 activity 中的 ui 操作。
- 以前我们通过在onceate 中调用  setContentView 绑定布局。

```
setContentView(R.layout.plain_activity)
```
- 现在我们用  `DataBindingUtil.setContentView` 方法绑定布局，如下。

```
val binding : ActivityKotlinBinding =   DataBindingUtil.setContentView(this, R.layout.activity_kotlin)
```
- ActivityKotlinBinding 这个是自动生成的类，类名称根据布局文件名称生成，将布局文件名称转换为`Pascal拼写法`并向其添加 Binding 后缀。
- 绑定的时候我们为什么要生成一个变量呢？因为我们需要通过它给布局变量赋值（我们之前在   `</data>` 便签中定义的）

```
    binding.name = "Your name"
    binding.lastName = "Your last name"
```
### 处理点击事件
- 在布局中添加 button 等布局，当点击按钮，会改变受欢迎程度的值likes，并用`progressbar` 显示进度，当likes大于4的时候改变头像图片，当likes 大于9 的时候改变图片的颜色。布局如下

```
<?xml version="1.0" encoding="utf-8"?>

<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <!--必须引入，否则下面的R.drawable.ic_person_black_96dp 的资源无法找到-->
        <import type="com.example.tuoanlan.databindingexample.R" />





    <!--定义布局变量用于之后的布局表达式 -->
        <variable
            name="user"
            type="com.example.tuoanlan.databindingexample.data.ObservableFieldProfile" />
    </data>

    <android.support.constraint.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainKotlinActivity">
        />

        <TextView

            android:id="@+id/plain_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="76dp"
            android:layout_marginTop="28dp"
            android:text="@{user.name,default=my_default}"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/plain_lastName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="76dp"
            android:layout_marginTop="16dp"
            android:text="@{user.lastName,default=my_default}"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/plain_name" />



        <TextView

            android:id="@+id/likes"
            android:layout_width="146dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:layout_marginEnd="8dp"
            android:layout_marginBottom="8dp"
            android:text="@{Integer.toString(user.likes),default=my_default}"
            android:textAppearance="@style/TextAppearance.AppCompat.Title"
            app:layout_constraintBottom_toTopOf="@+id/like_button"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/progressBar" />

        <ProgressBar
            android:id="@+id/progressBar"
            style="?android:attr/progressBarStyleHorizontal"
            android:layout_width="wrap_content"
            android:layout_height="11dp"
            android:layout_marginStart="8dp"
            android:layout_marginTop="172dp"
            android:layout_marginEnd="64dp"
            android:max="100"
            android:progress="@{user.likes}"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="1.0"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <Button
            android:id="@+id/like_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:layout_marginTop="108dp"
            android:layout_marginEnd="8dp"
            android:onClick="onLike"
            android:text="@string/like"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.875"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/progressBar" />


        <!-- Layout expressions can be *too* powerful. It's preferred   to avoid this type of
      view logic in the layout. Instead, use Binding Adapters (see ViewModelActivity).
      Also, `app:srcCompat` is bound to setImageResource.

        布局表达式可能太强大。最好避免这种类型的
        查看布局中的逻辑。而是使用绑定适配器（请参见ViewModelActivity）。
        另外，`app:srccompt`绑定到 setImageResource。就是这个类中的绑定方法 BindingMethods.kt
      -->
        <ImageView
            android:id="@+id/imageView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:layout_marginTop="8dp"
            android:layout_marginEnd="8dp"
            android:layout_marginBottom="8dp"
            app:layout_constraintBottom_toTopOf="@+id/progressBar"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.925"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="0.095"

            app:srcCompat="@{user.likes &lt; 4 ? R.drawable.ic_person_black_96dp : R.drawable.ic_whatshot_black_96dp }"
            android:tint="@{user.likes &gt; 9 ? @color/star : @android:color/black}"
            />

    </android.support.constraint.ConstraintLayout>
</layout>
```
**注意：有一下几点需要注意**
- 必须在 `BindingMethods.kt`（类随意，位置随意） 中定义@BindingMethods 将 app:srcCompat 翻译为 view.setImageResource(drawable) 方法，因为我们用`R.drawable.ic_person_black_96dp` 的方式为ImageView 设置图片
- 布局中必须引入 R 文件，否则无法找到相应的图片资源，方式如下 `<import type="com.example.tuoanlan.databindingexample.R" />
`
- 布局表达式很强大。可参考[binding 布局表达式](https://developer.android.com/topic/libraries/data-binding/expressions)，注意有些字符需要转义。

#### 定义点击方法 onLike ,并在布局中调用
- Activity  中的 onLike

```
class MainKotlinActivity : AppCompatActivity() {
    //当点击事件发生时调用，但这并不是推荐写法，因为activity 中耦合了业务代码
    //对应布局中的 onLike 方法
    fun onLike(view: View){
        user.likes.set(user.likes.get()+1)
    }
}

```

- 布局中调用 onLike 方法

```
  <Button
            android:id="@+id/like_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:layout_marginTop="108dp"
            android:layout_marginEnd="8dp"
            android:onClick="onLike"
            android:text="@string/like"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.875"
            app:layout_constraintStart_toStartOf="parent"
           />
```
- 这种处理点击事件的方法会和`activity `紧密偶尔，并不是官方推荐用法。后面会介绍结合 viewmode  一起使用。
------





### 参考文档
- [DataBinding使用指南(一)：布局和binding表达式](https://blog.csdn.net/guiying712/article/details/80206037#%E4%B8%8D%E6%94%AF%E6%8C%81%E7%9A%84%E6%93%8D%E4%BD%9C)



