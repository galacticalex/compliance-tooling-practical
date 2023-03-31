
-- SPDX-FileCopyrightText: Copyright 2022 Alex Murphy <amsupernova@pm.me>
--
-- SPDX-License-Identifier: Apache-2.0
--
-- My old vim configuration file (neovim can be configured with vimscript, or lua)


--[[ notes and resources

Generally good introduction.
https://vonheikemen.github.io/devlog/tools/configuring-neovim-using-lua/
:h 

]]--


-- 
-- General options.
--

-- Options.

vim.opt.number = true -- Show line numbers.
vim.opt.ignorecase = true -- Ignore capitalization (e.g., in searches).

vim.opt.expandtab = true -- Tabs become spaces.
vim.opt.tabstop = 4 -- Tabs are 4 spaces.
vim.opt.shiftwidth = 4 -- ^
vim.opt.softtabstop = 4 -- ^

vim.wo.colorcolumn = "110" -- Highlight column 110 for formatting.


-- Leader keys.

vim.g.mapleader = " " -- Both leaders are <Space>.
vim.g.maplocalleader = " " -- ^


--
-- Keybindings.
-- 

-- Normal mode navigating between visible windows.
vim.api.nvim_set_keymap("n", "<C-h>", "<C-w><C-h>", {noremap = true})
vim.api.nvim_set_keymap("n", "<C-j>", "<C-w><C-j>", {noremap = true})
vim.api.nvim_set_keymap("n", "<C-k>", "<C-w><C-k>", {noremap = true})
vim.api.nvim_set_keymap("n", "<C-l>", "<C-w><C-l>", {noremap = true})

-- Shorter normal mode shortcuts.
vim.api.nvim_set_keymap("n", ";", ":", {noremap = true})
vim.api.nvim_set_keymap("n", "<C-s>", "<Esc>:w<Enter>", {noremap = true})
vim.api.nvim_set_keymap("n", "<C-j>", "<Esc>:w<Enter>:source %<Enter>", {noremap = true})
vim.api.nvim_set_keymap("n", "TT", ":terminal<Enter>:set nonumber<Enter>a", {noremap = true})
vim.api.nvim_set_keymap("n", "TB", ":terminal<Enter>:set nonumber<Enter>arlwrap bb<Enter>", {noremap = true})

-- Shorter insert mode shortcuts.
vim.api.nvim_set_keymap("i", "jj", "<Esc>", {noremap = true})
vim.api.nvim_set_keymap("i", "<C-f>", "<Esc>A", {noremap = true})

-- Shorter terminal mode shortcuts.
vim.api.nvim_set_keymap("t", "<Esc>", "<C-\\><C-n>", {noremap = true})
vim.api.nvim_set_keymap("t", "jj", "<C-\\><C-n>", {noremap = true})
vim.api.nvim_set_keymap("t", "<C-f>", "<Right><Right><Right><Right><Right><Right>", {noremap = true})

-- Buffer navigation.
vim.api.nvim_set_keymap("n", "<leader>n", ":bn<Enter>", {noremap = true})
vim.api.nvim_set_keymap("n", "<leader>p", ":bp<Enter>", {noremap = true})
vim.api.nvim_set_keymap("t", "<leader>n", ":bn<Enter>", {noremap = true})
vim.api.nvim_set_keymap("t", "<leader>p", ":bp<Enter>", {noremap = true})


-- 
-- Plugin manager (vim-plug).
--

-- Unused at the moment
-- Plug 'Olical/conjure'
-- Plug 'jalvesaq/Nvim-R', {'branch': 'stable'}

vim.cmd([[

call plug#begin(stdpath('data') . '/plugged')

Plug 'NLKNguyen/papercolor-theme'
Plug 'rust-lang/rust.vim'
Plug 'jiangmiao/auto-pairs'

call plug#end()

]])


-- 
-- Colours: https://github.com/NLKNguyen/papercolor-theme
-- 

vim.cmd([[
set t_Co=256
set background=dark
colorscheme PaperColor
]])


--
-- Clojure specific.
--

-- Sometimes requires (clerk/serve! {:browse? true}) to be evaluated.

-- vim.cmd([[
-- function! ClerkShow()
--   exe "w"
--   exe "ConjureEval (nextjournal.clerk/show! \"" . expand("%:p") . "\")"
-- endfunction
-- 
-- nmap <silent> <localleader>cs :execute ClerkShow()<CR>
-- ]])


--
-- Syntax highlighting (plugin specific).
--

vim.cmd([[
    syntax enable
    filetype plugin indent on
]])


-- Motivation.

print("Get cracking!")

