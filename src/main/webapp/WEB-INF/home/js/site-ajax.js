/*
 *
 *
 */
 
var home_logo_switch = 0;//用以判断顶部是否缩上去
var popbanded = 0;//前进后退按钮只绑定一次

function loading_show(){
	if ( !$( '#loading-wrap .loading' )[0] ){
		$('#loading-wrap').append(_loading);
	}
	$( '#loading-wrap' ).css({'height':$(window).height()}).addClass('show');
	$( '#loading-wrap .loading' ).show().css({'margin-top':( $(window).height() - 120 ) / 2});
}
function loading_hide(){
	$( '#loading-wrap' ).removeClass('show');
	setTimeout(function(){//等待css animation动画缓冲完
		$( '#loading-wrap' ).css({'height':0});
		$( '#loading-wrap .loading' ).css({'margin-top':0}).hide();
	},800);
}

//js重载
function reload_js(){
	setTimeout(function(){
		//首页文章响应及相册页面响应
		
		if ( $( '.post-index' )[0] ){
			if( window.R_init !== undefined ){clearTimeout(responsive_time);R_init($( '#content' ),$( '.post-index article' ),297,12,0);}
		}else if ( $( '.album2' )[0] ){
			if( window.R_init !== undefined ){clearTimeout(responsive_time);R_init($( '#content' ),$( '.album2 article' ),220,12,0);}
		}
		//响应式调整js重载
		if( window.change_js !== undefined ){change_js();}
	},800);
	//logo隐藏与否
	if( window.logo_switch !== undefined ){logo_switch();}
	
	//文章播放器重载
	if( window.audio_ready !== undefined ){audio_ready();}
	
	//评论js重载
	if( window.do_comment_js !== undefined ){do_comment_js();}
	
	//相册重载
	if( window.gallery !== undefined ){gallery();}
	
	//文章内一些js重载
	if( window.article_js !== undefined ){article_js();}
	
	//历史页面js
	if( window.page_archive !== undefined ){page_archive();}
	
	//hermit
	if( window.hermitjs !== undefined ){
		hermitjs.reload();
	}
	
	ajax_post_bind();//重新绑定文章标题
}

//搜索和获取文章
function ajax_get_post(url,push_switch){
	$.ajax({
		url:url,
		type:'POST',
		dataType:'json',
		beforeSend:function(){
			$( '#main' ).fadeTo('fast',0.6);
			loading_show();
			$( '#narrow-menu' ).hide();narrow_menu_is_show = 0;
			
		},
		error:function(){
			alert('Ajax Error!请重试或检查网络是否出现问题。');
			$( '#main' ).stop().fadeTo('normal',1);
			loading_hide();
		},
		success:function(data){
			loading_hide();
			$('html,body').animate({scrollTop:0},0);
			$( '#main' ).html(data.postHtml).stop().fadeTo('normal',1);
			//logo隐藏与js重载
			reload_js();
			window.document.title = data.title;
			if ( push_switch == 1 ) {
				var state = {
					url: url,
				};
				window.history.pushState(state, data.title, data.href);
			}
			if ( popbanded == 0 ){
				window.addEventListener('popstate',function(e){
					if ( history.state ){
						ajax_get_post(history.state.url,0);//第二次退回
					}else{
						ajax_get_post(data.homeUrl,0);//第一次退回
					}
					//主菜单
					$( '.current-menu-item' ).removeClass('current-menu-item');
					$( '.current-menu-parent' ).removeClass('current-menu-parent');
					$( '.current-menu-ancestor' ).removeClass('current-menu-ancestor');
				},false);
				popbanded = 1;
			}
		}
	});
}

//获取文章前的一些处理
function ajax_start(_this){
	var post_id = _this.attr("id"),
		href = _this.attr("href"),
		url=Always.ajaxurl+"/getPost?id="+post_id,
		this_href = window.location.href;
		i = href.replace(this_href,'');
	//if ( href == this_href ) return false;
	if ( i == '#' ) return false;
	if ( !href.match(Always.ajaxurl ) ) return 0;
	//TODO if post_id=undefined
	ajax_get_post(url,1);
	
	//主菜单
	$( '.current-menu-item' ).removeClass('current-menu-item');
	$( '.current-menu-parent' ).removeClass('current-menu-parent');
	$( '.current-menu-ancestor' ).removeClass('current-menu-ancestor');
	return false;
	//return 1;
}
	
function ajax_menu(_this){
	var i = ajax_start(_this);
	if ( i == 1 ){
		//主菜单
		$( '.current-menu-item' ).removeClass('current-menu-item');
		$( '.current-menu-parent' ).removeClass('current-menu-parent');
		$( '.current-menu-ancestor' ).removeClass('current-menu-ancestor');
		return false;
	}
	return i;
}

function ajax_post_bind(){
	$( 'a.post-title' ).on('click',function(){//文章标题
		var i = ajax_start($(this));
		return i;
	});
	$( '.format-image a' ).on('click',function(){//图像文章首页图片
		var i = ajax_start($(this));
		return i;
	});
	
	$( '.entry-content a[target!=_blank]').each(function(){//文章内容中链接
		if ( !$(this).has('img')[0] ){
			$(this).on('click',function(){
				var i = ajax_start($(this));
				return i;
			});
		}
	});
	$( '.meta-tag a' ).on('click',function(){//文章标签
		var i = ajax_start($(this));
		return i;
	});
	$( '.page-navi a' ).on('click',function(){//文章分页
		var i = ajax_start($(this));
		return i;
	});
}

$(document).ready(function(){
	
	//依次绑定
	ajax_post_bind();
	
	//主菜单
	$( '.nav-menu a' ).on('click',function(){
		var i = ajax_start($(this));
		if ( i == false ){
			//主菜单
			$(this).parent().addClass('current-menu-item');
		}
		return i;
	});
	
	
	//搜索框
	$('#search-form').submit(function() {
		var s = $(this).find( '#s' ).val();
		if( /^$| /.test(s) ){
			return false;
		}
		//搜索参数s也可以放到数据库配置
		var href = $(this).attr('action') + '?s=' + encodeURIComponent(s);
		ajax_get_post(href,1);
		return false;
	});
	
});