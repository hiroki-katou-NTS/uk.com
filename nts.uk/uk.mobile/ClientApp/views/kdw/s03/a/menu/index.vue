<template>
<div class="kdws03a">
    <button type="button" class="btn btn-primary btn-block btn-lg">{{'KDWS03_24' | i18n}}</button>
    <button type="button" class="btn btn-primary btn-block btn-lg">{{'KDWS03_25' | i18n}}</button>
    <button type="button" class="btn btn-primary btn-block btn-lg" v-on:click="openKdws03c" v-bind:disabled="errorScreen">{{'KDWS03_30' | i18n}}</button>
    <button type="button" class="btn btn-primary btn-block btn-lg">{{'KDWS03_31' | i18n}}</button>
    <button type="button" class="btn btn-primary btn-block btn-lg">{{'KDWS03_32' | i18n}}</button>
    <button type="button" class="btn btn-primary btn-block btn-lg">{{'KDWS03_33' | i18n}}</button>
</div>
</template>