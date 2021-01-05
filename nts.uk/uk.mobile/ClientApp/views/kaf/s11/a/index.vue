<template>
<div class="kafs11a">
    <div>
        <kafs00-a v-if="kaf000_A_Params != null" v-bind:params="kaf000_A_Params" />
    </div>
    <div class="accordion" style="margin-bottom: 10px;">
        <div class="card">
            <div class="card-header uk-bg-accordion">
                <button class="btn btn-link" type="button">{{'KAFS11_1' | i18n}}</button>
            </div>
            <div class="collapse">
                <div class="card-body">
                    <table class="table table-sm mb-0">
                        <thead class="thead-light">
                            <tr class="table-light">
                                <td>{{'KAFS11_2' | i18n}}</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>3日</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div v-if="!$valid || !isValidateAll" class="card message-list error top-alert uk-text-danger topError">
        <button class="btn btn-link uk-text-danger">
            <i class="fa fa-exclamation-circle" aria-hidden="true" ></i>
            {{ 'KAFS11_3' | i18n }}
        </button>
    </div>
    <div class="card card-label">
        <div class="card-header uk-bg-accordion" style="align-items: center">
            <v-label class="border-0 pl-0 my-n3">
                {{'KAFS00_8' | i18n}}</v-label>
            <span class="badge badge-warning" style="height: 30%">必須</span>
        </div>
        <div class="card-body">
            <div style="width: 100%" id="prePostSelect">
                <nts-switchbox v-for="(option, optionIndex) in prePostResource" v-bind:key="optionIndex"
                    v-bind:disabled="false"
                    v-model="prePostAtr"
                    v-bind:value="option.code">
                        {{option.text | i18n}}
                </nts-switchbox>
            </div>
        </div>
    </div>
    <div class="card card-label">
        <div class="card-header uk-bg-accordion" style="align-items: center">
            <v-label class="border-0 pl-0 my-n3">
                {{'KAFS11_4' | i18n}}</v-label>
            <span class="badge badge-warning" style="height: 30%">必須</span>
        </div>
        <div class="card-body">
            <div style="width: 100%" id="prePostSelect">
                <nts-switchbox v-for="(option, optionIndex) in complementLeaveAtrResource" v-bind:key="optionIndex"
                    v-bind:disabled="false"
                    v-model="complementLeaveAtr"
                    v-bind:value="option.code">
                        {{option.text | i18n}}
                </nts-switchbox>
            </div>
        </div>
    </div>
    <div>
        <div class="card card-label">
            <div class="card-header uk-bg-sea-green" style="align-items: center">
                <v-label class="border-0 pl-0 my-n3">
                    {{'KAFS11_8' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <nts-date-input v-model="complementDate"/>
            </div>
        </div>
        <div class="card card-label">
            <div class="card-header uk-bg-accordion" style="align-items: center">
                <v-label class="border-0 pl-0 my-n3">
                    {{'KAFS11_9' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <div>
                    <button class="btn btn-selection">
                        <span class="uk-text-dark-gray" style="font-size: 90%">{{'KAFS11_10' | i18n}}</span>
                    </button>
                </div>
                <div>
                    <span class="uk-text-dark-gray ml-3" style="font-size: 90%">
                        <span class="font-weight-bold mr-3">{{ complementWorkType.code | i18n }}</span>
                        <span>{{ complementWorkType.name | i18n }}</span>
                    </span>       
                </div>
                <div>
                    <button class="btn btn-selection">
                        <span class="uk-text-dark-gray" style="font-size: 90%">{{'KAFS11_11' | i18n}}</span>
                    </button>      
                </div>
                <div>
                    <span class="uk-text-dark-gray ml-3" style="font-size: 90%">
                        <span class="font-weight-bold mr-3">{{ complementWorkTime.code | i18n }}</span>
                        <span>{{ complementWorkTime.name | i18n }}</span>
                    </span>    
                </div>
                <div class="mt-2 mb-2">
                    <span class="uk-text-dark-gray ml-3">
                        <span>{{ complementWorkTime.time | i18n }}</span>
                    </span>    
                </div>
            </div>
        </div>
        <div class="card card-label">
            <div class="card-header uk-bg-accordion" style="align-items: center">
                <v-label class="border-0 pl-0 my-n3">
                    {{'KAFS11_12' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <nts-time-range-input v-model="complementTimeRange1"/>
            </div>
        </div>
        <div class="card card-label">
            <div class="card-header uk-bg-accordion" style="align-items: center">
                <v-label class="border-0 pl-0 my-n3">
                    {{'KAFS11_13' | i18n}}</v-label>
                <span class="badge badge-info" style="height: 30%">任意</span>
            </div>
            <div class="card-body">
                <nts-time-range-input v-model="complementTimeRange2"/>
            </div>
        </div>
    </div>
    <div class="card card-label">
        <div class="card-header uk-bg-accordion" style="align-items: center">
            <v-label class="border-0 pl-0 my-n3">
                {{'KAFS11_19' | i18n}}</v-label>
            <span class="badge badge-info" style="height: 30%">任意</span>
        </div>
        <div class="card-body">
            <table class="table table-sm mb-0">
                <thead class="thead-light">
                    <tr class="table-light">
                        <td>{{'KAFS11_20' | i18n}}</td>
                        <td>{{'KAFS11_21' | i18n}}</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>20/04/04(日)</td>
                        <td>3日</td>
                    </tr>
                </tbody>
            </table>
            <div class="mt-2 mb-2" style="text-align: center">
                <button class="shadow-none btn rounded-pill btn-info">
                    <span class="ml-3">{{'KAFS11_22' | i18n}}</span>
                    <span class="fas fa-angle-double-right mr-3"></span>
                </button>
            </div>
        </div>
    </div>
    <div>
        <div class="card card-label">
            <div class="card-header uk-bg-sea-green" style="align-items: center">
                <v-label class="border-0 pl-0 my-n3">
                    {{'KAFS11_14' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <nts-date-input v-model="leaveDate"/>
            </div>
        </div>
        <div class="card card-label">
            <div class="card-header uk-bg-accordion" style="align-items: center">
                <v-label class="border-0 pl-0 my-n3">
                    {{'KAFS11_16' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <div>
                    <button class="btn btn-selection">
                        <span class="uk-text-dark-gray" style="font-size: 90%">{{'KAFS11_10' | i18n}}</span>
                    </button>
                </div>
                <div>
                    <span class="uk-text-dark-gray ml-3" style="font-size: 90%">
                        <span class="font-weight-bold mr-3">{{ leaveWorkType.code | i18n }}</span>
                        <span>{{ leaveWorkType.name | i18n }}</span>
                    </span>       
                </div>
                <div>
                    <button class="btn btn-selection">
                        <span class="uk-text-dark-gray" style="font-size: 90%">{{'KAFS11_11' | i18n}}</span>
                    </button>      
                </div>
                <div>
                    <span class="uk-text-dark-gray ml-3" style="font-size: 90%">
                        <span class="font-weight-bold mr-3">{{ leaveWorkTime.code | i18n }}</span>
                        <span>{{ leaveWorkTime.name | i18n }}</span>
                    </span>    
                </div>
                <div class="mt-2 mb-2">
                    <span class="uk-text-dark-gray ml-3">
                        <span>{{ leaveWorkTime.time | i18n }}</span>
                    </span>    
                </div>
            </div>
        </div>
        <div class="card card-label">
            <div class="card-header uk-bg-accordion" style="align-items: center">
                <v-label class="border-0 pl-0 my-n3">
                    {{'KAFS11_17' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <nts-time-range-input v-model="leaveTimeRange1"/>
            </div>
        </div>
        <div class="card card-label">
            <div class="card-header uk-bg-accordion" style="align-items: center">
                <v-label class="border-0 pl-0 my-n3">
                    {{'KAFS11_18' | i18n}}</v-label>
                <span class="badge badge-info" style="height: 30%">任意</span>
            </div>
            <div class="card-body">
                <nts-time-range-input v-model="leaveTimeRange2"/>
            </div>
        </div>
    </div>
    <div class="card card-label">
        <div class="card-header uk-bg-accordion" style="align-items: center">
            <v-label class="border-0 pl-0 my-n3">
                {{'KAFS11_19' | i18n}}</v-label>
            <span class="badge badge-info" style="height: 30%">任意</span>
        </div>
        <div class="card-body">
            <table class="table table-sm mb-0">
                <thead class="thead-light">
                    <tr class="table-light">
                        <td>{{'KAFS11_20' | i18n}}</td>
                        <td>{{'KAFS11_21' | i18n}}</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>20/04/04(日)</td>
                        <td>3日</td>
                    </tr>
                </tbody>
            </table>
            <div class="mt-2 mb-2" style="text-align: center">
                <button class="shadow-none btn rounded-pill btn-info">
                    <span class="ml-3">{{'KAFS11_22' | i18n}}</span>
                    <span class="fas fa-angle-double-right mr-3"></span>
                </button>
            </div>
        </div>
    </div>
    <div class="card card-label">
        <div class="card-header uk-bg-accordion" style="align-items: center">
            <v-label class="border-0 pl-0 my-n3">
                {{'KAFS11_23' | i18n}}</v-label>
            <span class="badge badge-info" style="height: 30%">任意</span>
        </div>
        <div class="card-body">
            <table class="table table-sm mb-0">
                <thead class="thead-light">
                    <tr class="table-light">
                        <td>{{'KAFS11_24' | i18n}}</td>
                        <td>{{'KAFS11_21' | i18n}}</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>20/04/04(日)</td>
                        <td>3日</td>
                    </tr>
                </tbody>
            </table>
            <div class="mt-2 mb-2" style="text-align: center">
                <button class="shadow-none btn rounded-pill btn-info">
                    <span class="ml-3">{{'KAFS11_25' | i18n}}</span>
                    <span class="fas fa-angle-double-right mr-3"></span>
                </button>
            </div>
        </div>
    </div>
    <div>
        <kafs00-c v-if="kaf000_C_Params != null" v-bind:params="kaf000_C_Params" 
        v-on:kaf000CChangeReasonCD="kaf000CChangeReasonCD"
        v-on:kaf000CChangeAppReason="kaf000CChangeAppReason" />
    </div>
    <div class="mb-3">
        <button class="btn btn-primary w-100">
            <span v-if="params">{{'KAFS11_30' | i18n}}</span>
            <span v-else>{{'KAFS11_29' | i18n}}</span>
        </button>
    </div>
</div>
</template>