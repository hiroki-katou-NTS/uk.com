/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg003.test.screenModel {

  @bean()
  export class ViewModel extends ko.ViewModel {
    url: string = window.location.origin + '/nts.uk.com.web/view/ccg/003/a/index.xhtml';
    html: string = '<iframe src="' + this.url + '"/>';

    created() {
      const vm = this;
      $('#popup-ccg003').ntsPopup({
        trigger: '#show-ccg003',
        position: {
          my: 'left top',
          at: 'left bottom',
          of: $('#show-ccg003')
        },
        showOnStart: false,
        dismissible: false
      });

      $('#show-ccg003').click(() => {
        $('#popup-ccg003').ntsPopup('show');
      });

    }

    close() {
      $('#popup-ccg003').ntsPopup('hide');
    }
  }
  
}