<div class="emailpaged" style="background: #fff;">
    <div class="emailcontent" style="width:100%;max-width:720px;text-align: left;margin: 0 auto;padding-top: 20px;padding-bottom: 80px">
        <div class="emailtitle" style="border-radius: 5px;border:1px solid #eee;overflow: hidden;">
            <h1 style="color:#fff;background: #3798e8;line-height:70px;font-size:24px;font-weight:normal;padding-left:40px;margin:0">
                您的网站有新的评论！
            </h1>
            <div class="emailtext" style="background:#fff;padding:20px 32px 40px;">
                <p style="color: #6e6e6e;font-size:13px;line-height:24px;">${author}, 您好!</p>
                <p style="color: #6e6e6e;font-size:13px;line-height:24px;">有访客在《${pageName}》留言:</p>
                    <br />
                <p style="color: #6e6e6e;font-size:13px;line-height:24px;padding:10px 20px;background:#f8f8f8;margin:0px">
                ${visitor}：${commentContent}
                </p>
                <br />
                <p style="color: #6e6e6e;font-size:13px;line-height:24px;">你可以点击<a href="${pageUrl}">查看完整内容</a></p>
            </div>
        </div>
    </div>
</div>