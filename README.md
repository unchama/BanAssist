# 免責事項
当プラグインでは、当プラグインの開発元ではない外部団体であるJapan Minecraft PvP(以下、JPMCPvP)が公開しているCompromised Accountによる処罰データを使用しています。当プラグインを使用して発生した障害や問題に関して、当プラグインの提供者及び処罰データの提供元であるJPMCPvPは一切の責任を負いません。当プラグイン導入の際には、利用者の自己の責任の元ご利用ください。<br>
<br>
# BanAssist
config.ymlで指定したjsonファイルからcompromised accountのみを抽出し、アクセスを禁止するプラグイン。<br>
初期設定ではJPMCPvPが公開している処罰リストをお借りしています。<br>
誤検知も考慮して、/ignoreコマンドでプレイヤーを追加するとCompromised accountであっても除外処理されます。<br>

# コマンドリファレンス
除外リストにプレイヤーを追加：/ignore add [name]<br>
除外リストからプレイヤーを削除：/ignore rem [name]<br>
除外リストに登録されている全プレイヤーを列挙：/ignore list<br>
対象プレイヤーが除外リストにあるかどうか確認：/ignore find [name]<br>
<br>
# SpecialThanks
当リポジトリは、CrossHearts氏が作成したコードをご厚意でunchamaが譲り受けたものです。<br>
この場をお借りして、感謝申し上げます。