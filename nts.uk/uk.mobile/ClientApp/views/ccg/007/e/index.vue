<template>
    <div id="reset-pass">
        <div class="text-center page-title">{{ 'CCGS07_18' | i18n }}</div>
        <div class="text-left page-info alert-light alert">
            <div>{{ 'CCGS07_9' | i18n(model.userName) }}</div>
            <div>{{ 'CCGS07_19' | i18n }}</div>
        </div>
        <fieldset class="login-form">
            <nts-input-password
                tabindex='1'
                class="control-label-inline"
                v-model="model.newPassword"
                name='CCGS07_11'
                v-bind:disabled="disabled"
                v-bind:show-title="true"
                v-bind:constraint="validations.model.newPassword"
                v-bind:columns="{ title: 'col-md-2', input: 'col-md-4' }" />
    
            <form v-submit="changePass">
                <nts-input-password
                    tabindex='2'
                    class="control-label-inline"
                    v-model="model.newPasswordConfirm"
                    name='CCGS07_12'
                    v-bind:disabled="disabled"
                    v-bind:show-title="true"
                    v-bind:constraint="validations.model.newPasswordConfirm"
                    v-bind:columns="{ title: 'col-md-2', input: 'col-md-4' }" />
            </form>
    
            <div class="accordion" v-if="policy.isUse">
                <div class="card">
                    <div class="card-header">
                        <button class="btn btn-link" type="button">
                            {{ 'CCGS07_13' | i18n }}
                        </button>
                    </div>
                    <div class="collapse">
                        <div class="card-body">
                            <table class="password-policy">
                                <tbody>
                                    <tr>
                                        <td><span>{{ 'CCGS07_22' | i18n }}</span></td>
                                        <td><span> {{ 'CCGS07_23' | i18n(policy.lowestDigits) }}</span></td>
                                    </tr>
                                    <tr v-if="policy.alphabetDigit > 0 || policy.numberOfDigits > 0 || policy.symbolCharacters > 0">
                                        <td><span>{{ 'CCGS07_24' | i18n }}</span></td>
                                        <td><span>{{ policy.complex }}</span></td>
                                    </tr>
                                    <tr v-if="policy.historyCount > 0">
                                        <td><span>{{ 'CCGS07_28' | i18n }}</span></td>
                                        <td><span>{{ 'CCGS07_29' | i18n(policy.historyCount) }}</span></td>
                                    </tr>
                                    <tr v-if="policy.validPeriod > 0">
                                        <td><span>{{ 'CCGS07_30' | i18n }}</span></td>
                                        <td><span>{{ 'CCGS07_31' | i18n(policy.validPeriod) }}</span></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <br/>
            <div class="mb-2">
                <button class="btn btn-primary btn-lg btn-block" v-click:500="changePass">{{ 'CCGS07_38' | i18n }}</button>
            </div>
        </fieldset>
    </div>
</template>
