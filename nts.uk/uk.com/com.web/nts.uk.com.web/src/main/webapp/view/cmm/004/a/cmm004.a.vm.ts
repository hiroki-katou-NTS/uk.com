/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.cmm004.a.screenModel {

  const API = {
    save: 'ctx/sys/gateway/singlesignon/saml/saveAuthSetting',
    operation: 'ctx/sys/gateway/login/sso/saml/operation/find',
    validation: 'ctx/sys/gateway/login/sso/saml/validation/find'
  };

  @bean()
  export class ViewModel extends ko.ViewModel {

    useSingleSignOn: KnockoutObservable<boolean> = ko.observable(false);
    idpRedirectUrl: KnockoutObservable<string> = ko.observable('');

    clientId: KnockoutObservable<string> = ko.observable('');
    idpEntityId: KnockoutObservable<string> = ko.observable('');
    idpCertificate: KnockoutObservable<string> = ko.observable('');

    created() {
      const vm = this;
      vm.initScreen();
    }

    initScreen() {
      const vm = this;
      vm.$blockui('grayout');
      $
        .when(vm.$ajax('com', API.operation), vm.$ajax('com', API.validation))
        .then((operation: SamlOperation, validation: SamlResponseValidation) => {
          if (!!operation) {
            vm.useSingleSignOn(operation.useSingleSignOn);
            vm.idpRedirectUrl(operation.idpRedirectUrl);
          }

          if (validation) {
            vm.clientId(validation.clientId);
            vm.idpEntityId(validation.idpEntityId);
            vm.idpCertificate(validation.idpCertificate);
          }
        })
        .always(() => vm.$blockui('clear'));
    }

    mounted() {
      const vm = this;
      $('#useSingleSignOn').focus();
      vm.useSingleSignOn.subscribe(value => {
        if (!value) {
          vm.idpRedirectUrl('');
          vm.clientId('');
          vm.idpEntityId('');
          vm.idpCertificate('');
          vm.$errors('clear');
        }
      })
    }

    register() {
      const vm = this;
      const handler = () => {
        const command = {
          useSingleSignOn: vm.useSingleSignOn(),
          idpRedirectUrl: vm.idpRedirectUrl(),
          clientId: vm.clientId(),
          idpEntityId: vm.idpEntityId(),
          idpCertificate: vm.idpCertificate(),
        };
  
        vm
          .$blockui('grayout')
          .then(() => vm.$ajax('com', API.save, command))
          .then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
          .always(() => vm.$blockui('clear'));
      }
      
      vm.$validate().then((valid) => {
        if (!valid) return;
        handler();
      });
    }
  }

  interface SamlOperation {
    useSingleSignOn: boolean;
    idpRedirectUrl: string;
  }

  interface SamlResponseValidation {
    clientId: string;
    idpEntityId: string;
    idpCertificate: string;
  }
}
