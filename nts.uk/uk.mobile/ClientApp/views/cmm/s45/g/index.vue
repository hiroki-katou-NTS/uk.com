<template>
<div class="cmms45g">
    <div class="modal-header" style="height: 37px;">
        {{'CMMS45_94' | i18n}}
    </div>
    <div class="col-12" style="font-size: large">
        {{ params.applicantName }}
    </div>
    <div class="col-12 pt-2">
        <div class="row">
            <div class="col-8" style="font-size: large">
                {{ params.appName }}
            </div>
            <div class="col-4 text-right" style="font-size: large">
                {{ params.appDate }}
            </div>
        </div>
    </div>
    <div class="col-12 pt-2 pb-2" v-html="topComment"></div>
    <div class="card-header pt-2">{{'CMMS45_95' | i18n}}</div>
    <div class="col-12 pt-2" v-bind:class="{ 'bg-pre-application-excess': isPreError, 'bg-workinh-result-excess': !isPreError }">
        <div v-html="appContent"></div>
    </div>
    <div class="col-12 pt-2" v-html="bottomComment"></div>
    <div class="col-12 pt-2">
        {{'CMMS45_96' | i18n}}
    </div>
    <div class="col-12 pt-2">
        <div class="row">
            <div class="col-6">
                <button class="btn btn-secondary btn-lg w-100" v-on:click="confirm">{{'Msg_35' | i18n}}</button>
            </div>
            <div class="col-6">
                <button class="btn btn-secondary btn-lg w-100" v-on:click="notConfirm">{{'Msg_36' | i18n}}</button>
            </div>
        </div>
    </div>
    <div class="col-12 pt-2" v-if="params.isMulti">
        <div class="row">
            <div class="col-6">
                <button class="btn btn-secondary btn-lg w-100" v-on:click="confirmAll">{{'Msg_379' | i18n}}</button>
            </div>
            <div class="col-6">
                <button class="btn btn-secondary btn-lg w-100" v-on:click="notConfirmAll">{{'Msg_380' | i18n}}</button>
            </div>
        </div>
    </div>
</div>
</template>