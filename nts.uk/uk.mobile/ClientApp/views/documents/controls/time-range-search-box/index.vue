<template>
<div class="documentscontrolssearch-box">
    <h5>1. {{'sample' | i18n}}</h5>
    <time-range-search-box v-on:search="searchList" />

    <br/>

    <markdown />

</div>
</template>
