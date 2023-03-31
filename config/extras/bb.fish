
# bb.fish
#
# SPDX-FileCopyrightText: 2023 Alexander Murphy <amsupernova@pm.me>
#
# SPDX-License-Identifier: Apache-2.0
#
# Fishscript alias to add history commands to babashka

function bb
    rlwrap bb $argv
end

