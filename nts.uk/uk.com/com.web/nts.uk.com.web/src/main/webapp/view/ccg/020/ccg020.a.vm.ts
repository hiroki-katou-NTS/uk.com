/// <reference path='../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.ccg020.a {

  @bean()
  export class CCG020Screen extends ko.ViewModel {
    created() {
      const vm = this;
      vm.addImgNotice();
      vm.addEventClickWarningBtn();
      vm.addEventClickNoticeBtn();
    }

    private addImgNotice() {
      let $userInfo = $('#user-info');
      let $message = $userInfo.find('#message');
      let $warningDisplay = $('<i/>').addClass('img');
      $warningDisplay.attr('id', 'warning-msg');
      $warningDisplay.attr('data-bind', 'ntsIcon: { no: 163, width: 20, height: 20 }');
      $warningDisplay.appendTo($message);

      let $noticeDisplay = $('<i/>').addClass('img');
      $noticeDisplay.attr('id', 'notice-msg');
      $noticeDisplay.attr('data-bind', 'ntsIcon: { no: 164, width: 20, height: 20 }');
      $noticeDisplay.appendTo($message);
    }

    private addEventClickNoticeBtn() {
      const vm = this;
      let $message = $('#message');
      let $warningMsg = $message.find('#notice-msg');
      $warningMsg.click(() => {
        alert( "Handler for notice.click() called." );
        vm.$blockui('grayout');
        // CCG003を起動する（パネルイメージで実行）
        vm.$window.modal('/view/ccg/003/index.xhtml').always(() => vm.$blockui('clear'));
      });
    }

    private addEventClickWarningBtn() {
      const vm = this;
      let $message = $('#message');
      let $warningMsg = $message.find('#warning-msg');
      $warningMsg.click(() => {
        alert( "Handler for warning.click() called." );
        // vm.$blockui('grayout');
        // vm.$window.modal('/view/ccg/003/index.xhtml').always(() => vm.$blockui('clear'));
      });
    }

  }
}