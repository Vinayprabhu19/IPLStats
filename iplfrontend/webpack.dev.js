const { merge } = require('webpack-merge');
const common = require('./webpack.config.js');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const webpack = require('webpack');
 module.exports =
	 merge(common, {
   mode: 'production',
	devServer: {
    historyApiFallback: true
	},
	plugins:[
		new BundleAnalyzerPlugin({
			excludeAssets:'/.*\.hot-update\.js/'
		}),
	],
 })
