var gulp        = require('gulp'),
    sass        = require('gulp-sass'),
    browserSync = require ('browser-sync'),
    concat      = require ('gulp-concat'),
    uglify      = require ('gulp-uglifyjs'),
    cssnano     = require ('gulp-cssnano'),
    rename      = require ('gulp-rename'),
    del         = require ('del'),
    imagemin    = require ('gulp-imagemin'),
    pngquant    = require ('imagemin-pngquant'),
    cache       = require ('gulp-cache'),
    autoprefixer = require ('gulp-autoprefixer');


gulp.task('mytask', function(){
console.log('Привет, я Таск!');
});

gulp.task('sass', function(){
    return gulp.src('sass/*.sass')
        .pipe(sass())
        .pipe(autoprefixer(['last 15 versions', '> 1%', 'ie 8', 'ie 7'], { cascade: true}))
        .pipe(gulp.dest('public/stylesheets'))
        .pipe(browserSync.reload({stream: true}))
});

gulp.task('scripts', function(){
    return gulp.src([
        'app/libs/jquery/dist/jquery.min.js',
        'app/libs/magnific-popup/dist/jquery.magnific-popup.js',
    ])
        .pipe(concat('libs.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('public/javascripts'));
});

gulp.task('css-libs', ['sass'], function () {
    return gulp.src('public/stylesheets/libs.css')
        .pipe(cssnano())
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest('public/stylesheets'));
});


gulp.task('browser-sync', function (){
    browserSync({
        proxy: 'localhost:9000',
        port: 9001,
        files: ['public/stylesheets/*.css', 'public/javascripts/*.js', 'app/views/*.html', './'],
        //open:false
    });
});

gulp.task('clean', function () {
    return del.sync('dist');
});

gulp.task('clear', function () {
    return cache.clearAll();
});

gulp.task('img', function () {
    return gulp.src('public/images/**/*')
        .pipe(cache(imagemin({
            interlaced: true,
            progressive: true,
            svgoPlugins: [{removeViewBox: false}],
            une: [pngquant()]
        })))
        .pipe(gulp.dest('dist/img'));
})


gulp.task('watch', ['browser-sync', 'css-libs', 'scripts'], function() { //в квадратных скобках - то, что выполнить раньше, чем 'watch'
    gulp.watch('sass/*.sass', ['sass']);
    gulp.watch('app/**/*.html', browserSync.reload);
    gulp.watch('app/**/*.js', browserSync.reload);
});

gulp.task('build', ['clean', 'img', 'sass', 'scripts'], function () {
    var buildCss = gulp.src([
        'public/stylesheets/main.css',
        'public/stylesheets/libs.min.css'])
            .pipe(gulp.dest('dist/css'));

    var buildFonts = gulp.src('public/fonts/**/*')
            .pipe(gulp.dest('dist/fonts'));

    var buildJs = gulp.src('public/javascripts/**/*')
        .pipe(gulp.dest('dist/js'));

    var buildHtml = gulp.src('app/views/**/*.html')
        .pipe(gulp.dest('dist'));
});
